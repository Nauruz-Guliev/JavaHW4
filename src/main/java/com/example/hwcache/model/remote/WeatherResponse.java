package com.example.hwcache.model.remote;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 0;
    @SerializedName(value = "temperature")
    private String temperature;
    @SerializedName(value = "description")
    private String description;
    @SerializedName(value = "forecast")
    private List<ForecastItem> forecast;
    @SerializedName(value = "wind")
    private String wind;
}