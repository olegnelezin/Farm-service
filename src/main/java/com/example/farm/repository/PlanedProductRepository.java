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
    boolean existsByProductAndDateAndEmployee(Product product, Date date, Employee employee);
    boolean existsByEmployeeAndDate(Employee employee, Date date);
    List<PlanedProduct> findAllByEmployeeAndDate(Employee employee, Date date);
    PlanedProduct findByProductAndDateAndEmployee(Product product, Date date, Employee employee);
    void deleteAllByEmployee(Employee employee);
    void deleteAllByEmployeeAndDate(Employee employee, Date date);
    void deleteAllByProduct(Product product);
}