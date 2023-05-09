package ru.kpfu.itis.gnt.registration.data;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class DataSource {

    public DriverManagerDataSource get() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = new Properties();

        try (FileInputStream in = new FileInputStream("C:\\Users\\Nauruz\\Desktop\\registration\\hw-registration\\src\\main\\resources\\app.properties")) {
            properties.load(in);
            dataSource.setDriverClassName(properties.getProperty("db.driver.className"));
            dataSource.setUrl(properties.getProperty("db.url"));
            dataSource.setUsername(properties.getProperty("db.username"));
            dataSource.setPassword(properties.getProperty("db.password"));
            return dataSource;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
