package com.example.farm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "planed_products")
public class PlanedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planedProductId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "count", nullable = false)
    private Long count;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @JoinColumn(name = "employee_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;

    public PlanedProduct(Product product, Long count,
                         Date date, Employee employee) {
        this.product = product;
        this.count = count;
        this.date = date;
        this.employee = employee;
    }
}
