package com.example.farm.model;

import com.example.farm.model.enams.EUnit;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "units_of_measurement")
@Data
@NoArgsConstructor
public class UnitOfMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer unitId;

    @Column(name = "unit", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private EUnit unit;
    public UnitOfMeasurement(EUnit unit) {
        this.unit = unit;
    }
}
