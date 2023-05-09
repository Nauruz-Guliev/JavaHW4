package ru.kpfu.itis.gnt.hwpebble.bloomfilter;

import java.util.List;
import java.util.function.ToIntFunction;

public class BloomFilterImpl<T> implements BloomFilter<T> {
    private final long[] array;

    /*
     * size need to be 1,2,4,8,16,32,64,128,256,512,1024,2048,4096...
     **/
    private final int size;

    // Hash Functions
    private final List<ToIntFunction<T>> hashFunctions;

    public BloomFilterImpl(long[] array, int logicalSize, List<ToIntFunction<T>> hashFunctions) {
        this.array = array;
        this.size = logicalSize;
        this.hashFunctions = hashFunctions;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private int size;
        private List<ToIntFunction<T>> hashFunctions;

        public Builder<T> withSize(int size) {
            if (Integer.bitCount(size) != 1){
                throw new IllegalArgumentException("Consider size being " +
                        "1,2,4,8,16,32,64,128,256,512,1024,2048,4096... Where Integer.bitCount(size) == 1 ");
            }
            this.size = size;
            return this;
        }

        public Builder<T> withHashFunctions(List<ToIntFunction<T>> hashFunctions) {
            this.hashFunctions = hashFunctions;
            return this;
        }

        public BloomFilterImpl<T> build() {
            return new BloomFilterImpl<>(new long[size >>> 6], size, hashFunctions);
        }
    }

    private int mapHash(int hash) {
        return hash & (size - 1);
    }
    @Override
    public void add(T value) {
        for (ToIntFunction<T> function : hashFunctions) {
            int hash = mapHash(function.applyAsInt(value));
            array[hash >>> 6] |= 1L << hash;
        }
    }

    @Override
    public boolean mightContain(T value) {
        for (ToIntFunction<T> function : hashFunctions) {
            int hash = mapHash(function.applyAsInt(value));
            if ((array[hash >>> 6] & (1L << hash)) == 0) {
                return false;
            }
        }
        return true;
    }
}
