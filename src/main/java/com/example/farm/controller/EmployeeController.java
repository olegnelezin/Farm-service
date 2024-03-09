package com.example.farm.controller;

import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.request.CollectedProductRequest;
import com.example.farm.service.CollectedProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final CollectedProductService collectedProductService;
    @PostMapping("/collect-product")
    public ResponseEntity<MessageDTO> collectProduct(Authentication authentication,
                                                     @RequestBody CollectedProductRequest request) {
        String email = (String) authentication.getPrincipal();
        return new ResponseEntity<>(
                new MessageDTO(collectedProductService.collectProducts(request, email)),
                HttpStatus.OK);
    }
}
