package com.example.farm.controller;

import com.example.farm.model.dto.EmployeeDTO;
import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.request.RegisterRequest;
import com.example.farm.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<EmployeeDTO> register(@RequestBody RegisterRequest request) {
        EmployeeDTO employee = adminService.register(request);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @GetMapping("/hello")
    public String hello() {
        return "few";
    }
}
