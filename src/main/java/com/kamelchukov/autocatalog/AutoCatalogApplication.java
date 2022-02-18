package com.kamelchukov.autocatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.common", "com.kamelchukov.autocatalog"})
public class AutoCatalogApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoCatalogApplication.class, args);
    }
}