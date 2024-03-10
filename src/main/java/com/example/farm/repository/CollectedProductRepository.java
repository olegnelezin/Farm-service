package com.example.farm.repository;

import com.example.farm.model.CollectedProduct;
import com.example.farm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CollectedProductRepository extends JpaRepository<CollectedProduct, Long> {
    List<CollectedProduct> findAllByDateAndEmployee(Date date, Employee employee);
    List<CollectedProduct> findAllByDateBetweenAndEmployee(Date start, Date end, Employee employee);

    List<CollectedProduct> findAllByDate(Date date);
    List<CollectedProduct> findAllByDateBetween(Date start, Date end);
}
