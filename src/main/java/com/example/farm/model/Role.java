package com.example.farm.model;

import com.example.farm.model.enams.ERole;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "role")

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private ERole name;
}
