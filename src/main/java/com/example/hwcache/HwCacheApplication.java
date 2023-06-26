package com.example.hwcache;

import com.example.hwcache.exception.EmptyWeatherResultException;
import com.example.hwcache.exception.WeatherResultException;
import com.example.hwcache.model.local.Weather;
import com.example.hwcache.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Scanner;


@SpringBootApplication
public class HwCacheApplication implements CommandLineRunner {

    @Autowired
    private WeatherService service;

    public static void main(String[] args) {
        new SpringApplicationBuilder(HwCacheApplication.class).web(WebApplicationType.NONE).run(args);
    }

    @Override
    public void run(String... args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("*** WEATHER CONSOLE APP *** \n");

            while (true) {
                System.out.print("Enter your city name: ");
                String cityName = scanner.nextLine();

                if (cityName.equalsIgnoreCase("exit")) break;

                Weather weather = service.getWeatherAopCached(cityName);

                System.out.printf("\nTemperature: %s \n", weather.getResponse().getTemperature());
                System.out.printf("Wind: %s \n", weather.getResponse().getWind());
                System.out.printf("Description: %s\n", weather.getResponse().getDescription());
                System.out.printf("Last updated: %s\n\n", Instant.ofEpochMilli(weather.getCreationTimeInMillis()).atZone(ZoneId.systemDefault()).toLocalDateTime());
            }
        } catch (EmptyWeatherResultException | WeatherResultException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
