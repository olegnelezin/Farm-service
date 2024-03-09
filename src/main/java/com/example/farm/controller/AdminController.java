package com.example.farm.controller;

import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.mapper.ProductMapper;
import com.example.farm.model.dto.EmployeeDTO;
import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.dto.ProductDTO;
import com.example.farm.model.request.DeleteRequest;
import com.example.farm.model.request.RegisterEmployeeRequest;
import com.example.farm.model.request.RegisterProductRequest;
import com.example.farm.service.AdminService;
import com.example.farm.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final ProductService productService;
    private final EmployeeMapper employeeMapper;
    private final ProductMapper productMapper;

    @PostMapping("/register-employee")
    public ResponseEntity<EmployeeDTO> registerEmployee(@RequestBody RegisterEmployeeRequest request) {
        EmployeeDTO employee = employeeMapper.toDTOFromEntity(adminService.registerEmployee(request));
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/delete-employee")
    public ResponseEntity<MessageDTO> deleteEmployee(@RequestBody DeleteRequest request) {
        MessageDTO message = new MessageDTO(adminService.deleteEmployee(request));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/register-product")
    public ResponseEntity<ProductDTO> registerProduct(@RequestBody RegisterProductRequest request) {
        ProductDTO product = productMapper.toDTOFromEntity(productService.saveProduct(request));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
