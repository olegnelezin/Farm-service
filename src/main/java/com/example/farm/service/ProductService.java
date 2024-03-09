package com.example.farm.service;

import com.example.farm.exception.EntityAlreadyExistsException;
import com.example.farm.exception.EntityDoesNotExistException;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.mapper.ProductMapper;
import com.example.farm.model.Employee;
import com.example.farm.model.Product;
import com.example.farm.model.UnitOfMeasurement;
import com.example.farm.model.dto.ProductDTO;
import com.example.farm.model.request.RegisterProductRequest;
import com.example.farm.repository.ProductRepository;
import com.example.farm.repository.UnitOfMeasurementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UnitOfMeasurementRepository unitOfMeasurementRepository;

    public Product saveProduct(RegisterProductRequest request) {

        if (productRepository.existsByName(request.getName())) {
            throw new EntityAlreadyExistsException("Product already exists.");
        }
        Product product = new Product(request.getName(), unitOfMeasurementRepository.findByUnit(request.getUnit()));
        return productRepository.save(product);
    }

    public Product getProductByName(String name) {
        return productRepository.findProductByName(name).orElseThrow(
                () -> new EntityDoesNotExistException("Employee does not exist.")
        );
    }
}
