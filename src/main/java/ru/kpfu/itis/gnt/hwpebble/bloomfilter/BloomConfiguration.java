package ru.kpfu.itis.gnt.hwpebble.bloomfilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.function.ToIntFunction;

@Configuration
public class BloomConfiguration {

    @Bean
    public BloomFilterImpl<Object> bloomFilter(

    ) throws IOException {
        String[] words = TextExtractor.extract(TextHolder.text);
        BloomFilterImpl<Object> filter = BloomFilterImpl
                .builder()
                .withSize(256)
                .withHashFunctions(hashFunction())
                .build();
        for(String word: words) {
            filter.add(word);
        }
        return filter;
    }

    @Bean
    public List<ToIntFunction<Object>> hashFunction() {
        return StringObjHash.build();
    }

}
