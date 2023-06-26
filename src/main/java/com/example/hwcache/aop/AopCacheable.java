package com.example.hwcache.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Locale;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AopCacheable {
    CachePeriod cachePeriod() default CachePeriod.TEN_MINUTES;

    CacheType cacheType() default CacheType.IN_MEMORY;

    enum CachePeriod {
        ONE_MINUTE(60_000L),
        TEN_MINUTES(10 * 60_000L),
        ONE_HOUR(6 * 10 * 60_000L),
        SIX_HOURS(6 * 6 * 10 * 60_000L),
        ONE_DAY(4 * 6 * 6 * 10 * 60_000L),
        WEEK(7 * 4 * 6 * 6 * 10 * 60_000L);

        private final Long value;

        public Long getValue() {
            return value;
        }

        CachePeriod(Long value) {
            this.value = value;
        }
    }

    enum CacheType {
        DATABASE,
        IN_MEMORY,
        REDIS
    }
}

