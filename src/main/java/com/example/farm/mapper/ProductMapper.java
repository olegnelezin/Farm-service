package com.example.farm.mapper;

import com.example.farm.model.Product;
import com.example.farm.model.UnitOfMeasurement;
import com.example.farm.model.dto.ProductDTO;
import com.example.farm.model.request.RegisterProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapper {
    public ProductDTO toDTOFromEntity(Product product) {
        return new ProductDTO(product.getProductId(), product.getName(), product.getUnit().getUnit().name());
    }
}
