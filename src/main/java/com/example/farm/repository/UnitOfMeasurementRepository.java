package com.example.farm.repository;

import com.example.farm.model.UnitOfMeasurement;
import com.example.farm.model.enams.EUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitOfMeasurementRepository extends JpaRepository<UnitOfMeasurement, Integer> {
    UnitOfMeasurement findByUnit(EUnit unit);
}