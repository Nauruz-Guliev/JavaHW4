package com.example.hwcache.model.remote;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class ForecastItem  implements Serializable {
    @Serial
    private static final long serialVersionUID = 0;
    @SerializedName(value = "temperature")
    private String temperature;
    @SerializedName(value = "day")
    private String day;
    @SerializedName(value = "wind")
    private String wind;
}