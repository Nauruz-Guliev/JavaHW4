package ru.kpfu.itis.gnt.hwpebble.bloomfilter;

public interface BloomFilter<T> {
    void add(T value);

    boolean mightContain(T value);
}
