package com.example.hwcache.exception;

public class WeatherResultException extends Exception {
    public WeatherResultException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public WeatherResultException(String message) {
        super(message);
    }
}
