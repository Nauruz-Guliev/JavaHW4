package ru.kpfu.itis.gnt.hwpebble.bloomfilter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;

public class StringObjHash implements ToIntFunction<Object> {

    @Override
    public int applyAsInt(Object value) {
        int hash = Objects.hash(value);
        return hash;
    }

    public static List<ToIntFunction<Object>> build(){
        return List.of(new StringObjHash());
    }
}
