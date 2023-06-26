package com.example.hwcache.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cache_object")
public class CacheObject implements Serializable {
    @Id
    private String key;
    @Column(length = Integer.MAX_VALUE)
    private String cachedObjectJson;
    private Long savedTime;
}
