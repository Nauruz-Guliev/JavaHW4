package com.example.hwcache.aop;


import com.example.hwcache.model.CacheObject;
import com.example.hwcache.repository.CachingRepository;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class CacheAspect {
    private final Map<String, CacheObject> cache;
    private final CachingRepository repository;
    private final Gson gson;
    private final ValueOperations<String, Object> valueOps;

    @Autowired
    public CacheAspect(CachingRepository repository, Gson gson, RedisTemplate<String, Object> redisTemplate) {
        this.repository = repository;
        this.cache = new HashMap<>();
        this.valueOps = redisTemplate.opsForValue();
        this.gson = gson;
    }


    @Around("@annotation(aopCacheable)")
    public Object aroundCacheableMethods(ProceedingJoinPoint joinPoint, AopCacheable aopCacheable) throws Throwable {
        AopCacheable.CacheType cacheType = aopCacheable.cacheType();
        AopCacheable.CachePeriod cachePeriod = aopCacheable.cachePeriod();
        Object result = null;
        String key = createKey(joinPoint);
        Long currentTime = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        Class<?> methodReturnClass = ((MethodSignature) joinPoint.getSignature()).getReturnType();

        switch (cacheType) {
            case DATABASE -> {
                // храним время в виде строки и любой объект в виде json
                // используется json, чтобы можно было кэшировать не только погоду, а что угодно
                Optional<CacheObject> cacheObject = repository.findById(key);
                if (cacheObject.isPresent() && (currentTime - cacheObject.get().getSavedTime()) < cachePeriod.getValue()) {
                    result = gson.fromJson(cacheObject.get().getCachedObjectJson(), methodReturnClass);
                } else {
                    // удаляем просроченные, если есть, чтобы не засорять бд
                    repository.deleteById(key);
                    // если результата в кэше не нашлось, то выполняем метод и сохраняем в кэше
                    result = joinPoint.proceed();
                    repository.save(CacheObject.builder()
                            .key(key)
                            .cachedObjectJson(gson.toJson(result))
                            .savedTime(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
                            .build());
                }
            }
            case IN_MEMORY -> {
                // сохраняем и достаем из hashmap, то есть хранится в оперативной памяти
                result = cache.get(key);
                if (result != null && (currentTime - ((CacheObject) result).getSavedTime()) < cachePeriod.getValue()) {
                    result = gson.fromJson(((CacheObject) result).getCachedObjectJson(), methodReturnClass);
                } else {
                    result = joinPoint.proceed();
                    cache.put(key, CacheObject.builder()
                            .cachedObjectJson(gson.toJson(result))
                            .savedTime(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
                            .build());
                }
            }
            // кэшируется не объект обертка, а сама погода, так как время сохранения хранить не требуется
            // redis всё это делает самостоятельно
            case REDIS -> {
                result = valueOps.get(key);
                if (result == null) {
                    result = joinPoint.proceed();
                    valueOps.set(key, result, cachePeriod.getValue(), TimeUnit.MILLISECONDS);
                }
            }
        }
        return result;
    }

    private String createKey(JoinPoint joinPoint) {
        StringBuilder key = new StringBuilder();
        key.append(joinPoint.getTarget().getClass().getName());
        key.append(".").append(joinPoint.getSignature().getName());
        key.append("(");
        for (Object methodArguments : joinPoint.getArgs()) {
            key.append(methodArguments.getClass().getSimpleName()).append("=").append(methodArguments).append(";");
        }
        key.append(")");
        return key.toString();
    }
}


