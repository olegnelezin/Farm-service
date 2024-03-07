package com.example.farm.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @GetMapping("/hello")
    String hello() {
        return "aboba";
    }
}
