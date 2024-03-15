package com.example.farm.service;

import com.example.farm.exception.EntityAlreadyExistsException;
import com.example.farm.exception.EntityDoesNotExistException;
import com.example.farm.model.Product;
import com.example.farm.model.request.admin.RegisterProductRequest;
import com.example.farm.repository.CollectedProductRepository;
import com.example.farm.repository.PlanedProductRepository;
import com.example.farm.repository.ProductRepository;
import com.example.farm.repository.UnitOfMeasurementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PlanedProductRepository planedProductRepository;
    private final CollectedProductRepository collectedProductRepository;
    private final UnitOfMeasurementRepository unitOfMeasurementRepository;

    @Transactional
    public String saveProduct(RegisterProductRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new EntityAlreadyExistsException("Product already exists.");
        }
        Product product = new Product(request.getName(), unitOfMeasurementRepository.findByUnit(request.getUnit()));
        productRepository.save(product);
        return "Product has been added.";
    }

    public Product getProductByName(String name) {
        return productRepository.findProductByName(name).orElseThrow(
                () -> new EntityDoesNotExistException("Product does not exist.")
        );
    }

    @Transactional
    public String deleteProductByName(String name) {
        Product product = getProductByName(name);
        planedProductRepository.deleteAllByProduct(product);
        collectedProductRepository.deleteAllByProduct(product);
        productRepository.deleteByName(name);
        return "Product has been deleted.";
    }
}
