package com.example.farm;

import com.example.farm.model.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringBootApplication
public class FarmApplication {
    // credentials for admin
    // email: relex@gmail.com
    // password: admin
    public static void main(String[] args) {
        SpringApplication.run(FarmApplication.class, args);
    }

}
