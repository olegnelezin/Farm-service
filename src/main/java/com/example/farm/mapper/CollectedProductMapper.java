package com.example.farm.mapper;

import com.example.farm.model.CollectedProduct;
import com.example.farm.model.dto.EmployeeCollectedProductDTO;
import com.example.farm.model.dto.FarmCollectedProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CollectedProductMapper {

    /**
     * Преобразует CollectedProduct в CollectedProductDTO.
     * После блока for выполняется алгоритм для более удобного
     * представления списка.
     * JSON без использования данного алгоритма:
     * <pre>
     * [
     *     {
     *         "email": "astravsu@gmail.com",
     *         "productName": "apple",
     *         "unit": "unit",
     *         "count": 4,
     *         "date": "2024-03-09"
     *     },
     *     {
     *         "email": "astravsu@gmail.com",
     *         "productName": "apple",
     *         "unit": "unit",
     *         "count": 3,
     *         "date": "2024-03-09"
     *     }
     * ]
     * </pre>
     *
     * JSON с использованием данного алгоритма:
     * <pre>
     * [
     *     {
     *         "email": "astravsu@gmail.com",
     *         "productName": "apple",
     *         "unit": "unit",
     *         "count": 7,
     *         "date": "2024-03-09"
     *     }
     * ]
     * </pre>
     */

    public List<EmployeeCollectedProductDTO> fromEntityToEmployeeCollectedProductDTO(List<CollectedProduct> collectedProducts) {
        List<EmployeeCollectedProductDTO> collectedProductDTOS = new ArrayList<>();
        for (CollectedProduct collectedProduct: collectedProducts) {
            collectedProductDTOS.add(new EmployeeCollectedProductDTO(
                    collectedProduct.getEmployee().getEmail(),
                    collectedProduct.getProduct().getName(),
                    collectedProduct.getProduct().getUnit().getUnit().name(),
                    collectedProduct.getCount()
            ));
        }
        // Алгоритм
        int i = 0;
        while (i != collectedProductDTOS.size() - 1 && !collectedProductDTOS.isEmpty()) {
            if (collectedProductDTOS.get(i).getProductName().equals(
                    collectedProductDTOS.get(i + 1).getProductName())) {

                if (collectedProductDTOS.get(i).getEmail().equals(
                        collectedProductDTOS.get(i + 1).getEmail())) {
                    Long sum = collectedProductDTOS.get(i + 1).getCount();
                    collectedProductDTOS.get(i + 1).setCount(sum + collectedProductDTOS.get(i).getCount());
                    collectedProductDTOS.remove(i);
                }
            } else {
                i++;
            }
        }
        return collectedProductDTOS;
    }

    public List<FarmCollectedProductDTO> fromEntityToFarmCollectedProductDTO(List<CollectedProduct> collectedProducts) {
        List<FarmCollectedProductDTO> collectedProductDTOS = new ArrayList<>();
        for (CollectedProduct collectedProduct: collectedProducts) {
            collectedProductDTOS.add(new FarmCollectedProductDTO(
                    collectedProduct.getProduct().getName(),
                    collectedProduct.getProduct().getUnit().getUnit().name(),
                    collectedProduct.getCount()
            ));
        }
        // Алгоритм
        int i = 0;
        while (i != collectedProductDTOS.size() - 1 && !collectedProductDTOS.isEmpty()) {
            if (collectedProductDTOS.get(i).getProductName().equals(
                    collectedProductDTOS.get(i + 1).getProductName())) {
                    Long sum = collectedProductDTOS.get(i + 1).getCount();
                    collectedProductDTOS.get(i + 1).setCount(sum + collectedProductDTOS.get(i).getCount());
                    collectedProductDTOS.remove(i);
            } else {
                i++;
            }
        }
        return collectedProductDTOS;
    }
}
