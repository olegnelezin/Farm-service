package com.example.farm.repository;

import com.example.farm.model.PlanedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanedProductRepository extends JpaRepository<PlanedProduct, Long> {

}
