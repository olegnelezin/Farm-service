package com.example.farm.controller;

import com.example.farm.mapper.CollectedProductMapper;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.mapper.ProductMapper;
import com.example.farm.model.dto.*;
import com.example.farm.model.request.*;
import com.example.farm.service.CollectedProductService;
import com.example.farm.service.EmployeeService;
import com.example.farm.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final EmployeeService employeeService;
    private final ProductService productService;
    private final EmployeeMapper employeeMapper;
    private final ProductMapper productMapper;
    private final CollectedProductService collectedProductService;
    private final CollectedProductMapper collectedProductMapper;

    @PostMapping("/register-employee")
    public ResponseEntity<EmployeeDTO> registerEmployee(@RequestBody RegisterEmployeeRequest request) {
        EmployeeDTO employee = employeeMapper.toDTOFromEntity(employeeService.saveEmployee(request));
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/delete-employee")
    public ResponseEntity<MessageDTO> deleteEmployee(@RequestBody DeleteRequest request) {
        MessageDTO message = new MessageDTO(employeeService.deleteEmployeeByEmail(request));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/register-product")
    public ResponseEntity<ProductDTO> registerProduct(@RequestBody RegisterProductRequest request) {
        ProductDTO product = productMapper.toDTOFromEntity(productService.saveProduct(request));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/get-collected-products/by-employee")
    public ResponseEntity<List<EmployeeCollectedProductDTO>> getProductsByEmployee(@RequestBody GetCollectedProductsEmployeeRequest request) {
        List<EmployeeCollectedProductDTO> collectedProducts =
                collectedProductMapper.fromEntityToEmployeeCollectedProductDTO(
                        collectedProductService.getCollectedProductsByPeriod(request)
        );
        return new ResponseEntity<>(collectedProducts, HttpStatus.OK);
    }

    @PostMapping("/get-collected-products/by-farm")
    public ResponseEntity<List<FarmCollectedProductDTO>> getProductsByFarm(@RequestBody GetCollectedProductsFarmRequest request) {
        List<FarmCollectedProductDTO> collectedProducts =
                collectedProductMapper.fromEntityToFarmCollectedProductDTO(
                        collectedProductService.getCollectedProductsByPeriod(request)
                );
        return new ResponseEntity<>(collectedProducts, HttpStatus.OK);
    }
}
