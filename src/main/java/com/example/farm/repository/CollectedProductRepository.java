package com.example.farm.repository;

import com.example.farm.model.CollectedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectedProductRepository extends JpaRepository<CollectedProduct, Long> {
}
