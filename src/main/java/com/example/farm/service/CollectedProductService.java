package com.example.farm.service;

import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.model.CollectedProduct;
import com.example.farm.model.Employee;
import com.example.farm.model.Product;
import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.request.CollectedProductRequest;
import com.example.farm.repository.CollectedProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;


@AllArgsConstructor
@Service
public class CollectedProductService {
    private final EmployeeService employeeService;
    private final ProductService productService;
    private final CollectedProductRepository collectedProductRepository;
    public String collectProducts(CollectedProductRequest request, String email) {
        Employee employee = employeeService.getEmployeeByEmail(email);
        Product product = productService.getProductByName(request.getProductName());
        CollectedProduct collectedProduct = new CollectedProduct(
                product,
                request.getCount(),
                new GregorianCalendar().getTime(),
                employee
        );
        collectedProductRepository.save(collectedProduct);
        return "Products have been collected.";
    }
}
