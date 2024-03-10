package com.example.farm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "collected_products")
public class CollectedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CollectedProductId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "count", nullable = false)
    private Long count;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public CollectedProduct(Product product, Long count, Date date, Employee employee) {
        this.product = product;
        this.count = count;
        this.date = date;
        this.employee = employee;
    }
}
