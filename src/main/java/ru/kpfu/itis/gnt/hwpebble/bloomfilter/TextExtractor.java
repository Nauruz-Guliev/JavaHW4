package ru.kpfu.itis.gnt.hwpebble.bloomfilter;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TextExtractor {
    public static String[] extract(String content) throws IOException {
        return content.split(" ");
    }
}
