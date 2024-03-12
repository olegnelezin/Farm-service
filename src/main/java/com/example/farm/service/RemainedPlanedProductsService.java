package com.example.farm.service;

import com.example.farm.model.CollectedProduct;
import com.example.farm.model.Employee;
import com.example.farm.model.Product;
import com.example.farm.model.request.employee.CollectProductRequest;
import com.example.farm.repository.CollectedProductRepository;
import com.example.farm.repository.PlanedProductRepository;
import com.example.farm.util.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RemainedPlanedProductsService {
    private final EmployeeService employeeService;
    private final CollectedProductRepository collectedProductRepository;
    private final ProductService productService;
    private final PlanedProductService planedProductService;
    private final PlanedProductRepository planedProductRepository;

    public String getRemainedCountMessage(CollectProductRequest request, String email) {
        Long collectedCount = getAmountCountOfProduct(request, email);
        Product product = productService.getProductByName(request.getProductName());

        if (!planedProductRepository.existsByProductAndDate(product, DateUtils.getCurrentDay())) {
            return "There is no plan for this product.";
        }

        Long planedCount = planedProductService.getPlanedProductByProductAndDate(product, DateUtils.getCurrentDay()).getCount();
        if (collectedCount > planedCount) {
            return "You already collected all planed " + request.getProductName() + ".";
        }
        if (planedCount == 0) {
            return "There is no plan for this product.";
        }
            long differenceBetween = planedCount - collectedCount;
            return "Remained " + request.getProductName() + " products: " + differenceBetween;
    }

    private Long getAmountCountOfProduct(CollectProductRequest request, String email) {
        Employee employee = employeeService.getEmployeeByEmail(email);
        List<CollectedProduct> collectedProducts = collectedProductRepository.
                findAllByDateAndEmployee(DateUtils.getCurrentDay(), employee);
        Product product = productService.getProductByName(request.getProductName());
        return amount(collectedProducts, product);
    }

    private Long amount(List<CollectedProduct> collectedProducts, Product product) {
        Long amountCount = 0L;
        for(CollectedProduct collectedProduct: collectedProducts) {
            if (collectedProduct.getProduct().getName().equalsIgnoreCase(product.getName())) {
                amountCount += collectedProduct.getCount();
            }
        }
        return amountCount;
    }
}
