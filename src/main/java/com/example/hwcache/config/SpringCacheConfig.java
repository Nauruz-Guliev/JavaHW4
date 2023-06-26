package com.example.hwcache.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalTime;

import static com.example.hwcache.constants.CacheKeys.WEATHER_KEY;

@Configuration
@EnableScheduling
@EnableCaching
public class SpringCacheConfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(WEATHER_KEY);
    }

    // кэш будет сбрасываться каждые 10 минут также будет выводиться об этом лог
    @CacheEvict(allEntries = true, value = {WEATHER_KEY})
    @Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 10000)
    public void notifyCacheFlush() {
        System.err.printf("Cache flushed at: %s \n", LocalTime.now());
    }
}
