package com.example.farm.repository;

import com.example.farm.model.CollectedProduct;
import com.example.farm.model.Employee;
import com.example.farm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Когда вызыватся эти методы, происходит сортировка по возрастанию productId.
 * Это нужно для корректной работы класса CollectedProductMapper.
 */

@Repository
public interface CollectedProductRepository extends JpaRepository<CollectedProduct, Long> {
    List<CollectedProduct> findAllByDateAndEmployeeOrderByProduct(Date date, Employee employee);
    List<CollectedProduct> findAllByDateBetweenAndEmployeeOrderByProduct(Date start, Date end, Employee employee);
    List<CollectedProduct> findAllByDateOrderByProduct(Date data);
    List<CollectedProduct> findAllByDateBetweenOrderByProduct(Date start, Date end);
    void deleteAllByEmployee(Employee employee);
    void deleteAllByProduct(Product product);
}
