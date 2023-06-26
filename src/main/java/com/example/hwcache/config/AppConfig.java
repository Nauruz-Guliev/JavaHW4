package com.example.hwcache.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class AppConfig {

    private static final String WEATHER_API_BASE_URL = "https://goweather.herokuapp.com/";

    @Bean
    public Retrofit weatherRetrofit(GsonConverterFactory gsonConverterFactory,
                                    OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(WEATHER_API_BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .build();
    }

    @Bean
    public OkHttpClient client(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Bean
    public HttpLoggingInterceptor okHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return logging;
    }

    @Bean
    public GsonConverterFactory gsonConverterFactory() {
        return GsonConverterFactory.create();
    }


    @Bean
    public Gson gson() {
        return new Gson();
    }
}
