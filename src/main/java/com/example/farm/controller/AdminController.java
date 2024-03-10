package com.example.farm.controller;

import com.example.farm.mapper.CollectedProductMapper;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.mapper.ProductMapper;
import com.example.farm.model.dto.*;
import com.example.farm.model.request.*;
import com.example.farm.service.CollectedProductService;
import com.example.farm.service.EmployeeMarkService;
import com.example.farm.service.EmployeeService;
import com.example.farm.service.ProductService;
import lombok.AllArgsConstructor;
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
    private final EmployeeMarkService employeeMarkService;
    private final CollectedProductService collectedProductService;
    private final CollectedProductMapper collectedProductMapper;

    @PostMapping("/register-employee")
    public EmployeeDTO registerEmployee(@RequestBody RegisterEmployeeRequest request) {
        return employeeMapper.toDTOFromEntity(employeeService.saveEmployee(request));
    }

    @PostMapping("/delete-employee")
    public MessageDTO deleteEmployee(@RequestBody DeleteRequest request) {
        return new MessageDTO(employeeService.deleteEmployeeByEmail(request));
    }

    @PostMapping("/register-product")
    public ProductDTO registerProduct(@RequestBody RegisterProductRequest request) {
        return productMapper.toDTOFromEntity(productService.saveProduct(request));
    }

    @PostMapping("/get-collected-products/by-employee")
    public List<EmployeeCollectedProductDTO> getProductsByEmployee(@RequestBody GetCollectedProductsEmployeeRequest request) {
        List<EmployeeCollectedProductDTO> collectedProducts =
                collectedProductMapper.fromEntityToEmployeeCollectedProductDTO(
                        collectedProductService.getCollectedProductsByPeriod(request)
        );
        return collectedProducts;
    }

    @PostMapping("/get-collected-products/by-farm")
    public List<FarmCollectedProductDTO> getProductsByFarm(@RequestBody GetCollectedProductsFarmRequest request) {
        List<FarmCollectedProductDTO> collectedProducts =
                collectedProductMapper.fromEntityToFarmCollectedProductDTO(
                        collectedProductService.getCollectedProductsByPeriod(request)
                );
        return collectedProducts;
    }

    @PostMapping("/set-mark-for-employee")
    public MessageDTO setMarkForEmployee(@RequestBody SetMarkRequest request) {
        return new MessageDTO(employeeMarkService.saveEmployeeMark(request));
    }
}
