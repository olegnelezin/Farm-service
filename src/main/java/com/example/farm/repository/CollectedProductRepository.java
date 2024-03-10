package com.example.farm.repository;

import com.example.farm.model.CollectedProduct;
import com.example.farm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Когда вызыватся эти методы, происходит сортировка по возрастанию productId.
 * Это нужно для корректной работы класса CollectedProductMapper.
 */

@Repository
public interface CollectedProductRepository extends JpaRepository<CollectedProduct, Long> {
    @Query("SELECT e FROM CollectedProduct e ORDER BY e.product.productId ASC")
    List<CollectedProduct> findAllByDateAndEmployee(Date date, Employee employee);
    @Query("SELECT e FROM CollectedProduct e ORDER BY e.product.productId ASC")
    List<CollectedProduct> findAllByDateBetweenAndEmployee(Date start, Date end, Employee employee);
    @Query("SELECT e FROM CollectedProduct e ORDER BY e.product.productId ASC")
    List<CollectedProduct> findAllByDate(Date data);
    @Query("SELECT e FROM CollectedProduct e ORDER BY e.product.productId ASC")
    List<CollectedProduct> findAllByDateBetween(Date start, Date end);
}
