package com.example.hwcache.model.local;

import com.example.hwcache.model.remote.WeatherResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder
public class Weather implements Serializable {
    @Serial
    private static final long serialVersionUID = 0;
    private WeatherResponse response;
    private Long creationTimeInMillis;
}
