package com.example.hwcache.service;

import com.example.hwcache.aop.AopCacheable;
import com.example.hwcache.api.WeatherApi;
import com.example.hwcache.exception.EmptyWeatherResultException;
import com.example.hwcache.exception.WeatherResultException;
import com.example.hwcache.model.local.Weather;
import com.example.hwcache.model.remote.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import static com.example.hwcache.aop.AopCacheable.CachePeriod.ONE_MINUTE;
import static com.example.hwcache.aop.AopCacheable.CacheType.DATABASE;
import static com.example.hwcache.aop.AopCacheable.CacheType.REDIS;
import static com.example.hwcache.constants.CacheKeys.WEATHER_KEY;

@Service
@CacheConfig(cacheNames = {WEATHER_KEY})
public class WeatherService {
    private final WeatherApi weatherApi;

    @Autowired
    public WeatherService(Retrofit retrofit) {
        this.weatherApi = retrofit.create(WeatherApi.class);
    }

    @Cacheable(key = "#cityName")
    public Weather getWeatherSpringCached(String cityName) throws WeatherResultException, EmptyWeatherResultException {
        return getWeather(cityName);
    }

    @AopCacheable(cachePeriod = ONE_MINUTE, cacheType = DATABASE)
    public Weather getWeatherAopCached(String cityName) throws WeatherResultException, EmptyWeatherResultException {
        return getWeather(cityName);
    }

    private Weather getWeather(String cityName) throws WeatherResultException, EmptyWeatherResultException {
        try {
            Response<WeatherResponse> response = weatherApi.getWeather(cityName).execute();
            if (response.isSuccessful()) {
                WeatherResponse result = response.body();
                // api возвращает пустые поля, если город не был найден
                if (result != null && !result.getDescription().isBlank() && !result.getTemperature().isBlank() && !result.getWind().isBlank()) {
                    return new Weather(result, LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
                } else {
                    throw new EmptyWeatherResultException("Weather result for city " + cityName + " was not found.");
                }
            } else {
                throw new WeatherResultException("Remote server responded with code: " + response.code());
            }
        } catch (IOException | WeatherResultException exception) {
            throw new WeatherResultException("Failed to retrieve weather result", exception);
        }
    }
}
