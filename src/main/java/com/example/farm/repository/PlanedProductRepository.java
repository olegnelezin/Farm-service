package com.example.farm.repository;

import com.example.farm.model.Employee;
import com.example.farm.model.PlanedProduct;
import com.example.farm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlanedProductRepository extends JpaRepository<PlanedProduct, Long> {
    boolean existsByEmployeeAndDateAndProduct(Employee employee, Date date, Product product);
    boolean existsByProductAndDate(Product product, Date date);
    List<PlanedProduct> findAllByEmployeeAndDate(Employee employee, Date date);

    PlanedProduct findByProductAndDate(Product product, Date date);
    void deleteAllByEmployee(Employee employee);
}