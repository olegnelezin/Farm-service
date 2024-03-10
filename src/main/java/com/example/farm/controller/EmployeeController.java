package com.example.farm.controller;

import com.example.farm.model.dto.MarkDTO;
import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.request.CollectedProductRequest;
import com.example.farm.service.CollectedProductService;
import com.example.farm.service.EmployeeMarkService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final CollectedProductService collectedProductService;
    private final EmployeeMarkService employeeMarkService;
    @PostMapping("/collect-product")
    public MessageDTO collectProduct(Authentication authentication,
                                                     @RequestBody CollectedProductRequest request) {
        String email = (String) authentication.getPrincipal();
        return new MessageDTO(collectedProductService.collectProducts(request, email));
    }

    @GetMapping("/get-my-mark")
    public MarkDTO collectProduct(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return new MarkDTO(employeeMarkService.getEmployeeMark(email));
    }
}
