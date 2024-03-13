package com.example.farm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class FarmApplication {
    // credentials for admin
    // email: relex@gmail.com
    // password: admin
    public static void main(String[] args) {
        SpringApplication.run(FarmApplication.class, args);
    }

}
