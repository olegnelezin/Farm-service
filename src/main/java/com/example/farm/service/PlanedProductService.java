package com.example.farm.service;

import com.example.farm.exception.EntityAlreadyExistsException;
import com.example.farm.exception.EntityDoesNotExistException;
import com.example.farm.model.Employee;
import com.example.farm.model.PlanedProduct;
import com.example.farm.model.Product;
import com.example.farm.model.request.admin.SetPlanRequest;
import com.example.farm.repository.PlanedProductRepository;
import com.example.farm.util.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class PlanedProductService {
    private final PlanedProductRepository planedProductRepository;
    private final ProductService productService;
    private final EmployeeService employeeService;

    @Transactional
    public String setEmployeePlan(SetPlanRequest request) {
        Product product = productService.getProductByName(request.getProductName());
        Employee employee = employeeService.getEmployeeByEmail(request.getEmail());
        PlanedProduct planedProduct = new PlanedProduct(product, request.getCount(), DateUtils.getCurrentDay(), employee);
        if (planedProductRepository.existsByEmployeeAndDateAndProduct(employee, DateUtils.getCurrentDay(), product)) {
            throw new EntityAlreadyExistsException("Plan on this product already set.");
        }
        planedProductRepository.save(planedProduct);
        return "Plan has been added.";
    }

    public List<PlanedProduct> getEmployeePlan(String email) {
        Date date = DateUtils.getCurrentDay();
        Employee employee = employeeService.getEmployeeByEmail(email);
        return planedProductRepository.findAllByEmployeeAndDate(employee, date);
    }

    public PlanedProduct getPlanedProductByProductAndDate(Product product, Date date, Employee employee) {
        if (!planedProductRepository.existsByProductAndDateAndEmployee(product, date, employee)) {
            throw new EntityDoesNotExistException("Planed Product does not exist.");
        }
        return planedProductRepository.findByProductAndDateAndEmployee(product, date, employee);
    }
}
