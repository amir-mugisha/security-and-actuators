package org.example.product_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SpringBootApplication
@EnableCaching
public class ProductManagementSystemApplication {
    private static final Logger log = LoggerFactory.getLogger(ProductManagementSystemApplication.class);
    public static void main(String[] args) {
        log.info("Application getting started");

        SpringApplication.run(ProductManagementSystemApplication.class, args);
    }

}
