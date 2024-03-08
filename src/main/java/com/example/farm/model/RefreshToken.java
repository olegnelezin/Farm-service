package com.example.farm.model;


import com.example.farm.model.Employee;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_token")
@Data
@NoArgsConstructor
public class RefreshToken {
    @Id
    private UUID token;

    @Column(nullable = false)
    private Instant validTill;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
