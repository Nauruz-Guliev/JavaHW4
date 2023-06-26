package com.example.hwcache.api;

import com.example.hwcache.model.remote.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherApi {
    @GET("weather/{city}")
    Call<WeatherResponse> getWeather(@Path("city") String cityName);
}
