package com.example.farm.mapper;

import com.example.farm.model.PlanedProduct;
import com.example.farm.model.dto.PlanDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PlanedProductMapper {
    public List<PlanDTO> toDTOFromEntity(List<PlanedProduct> planedProducts) {
        List<PlanDTO> planedProductsDTO = new ArrayList<>();

        for (PlanedProduct planedProduct: planedProducts) {
            planedProductsDTO.add(new PlanDTO(
                    planedProduct.getProduct().getName(),
                    planedProduct.getProduct().getUnit().getUnit().name(),
                    planedProduct.getCount()
            ));
        }
        return planedProductsDTO;
    }
}
