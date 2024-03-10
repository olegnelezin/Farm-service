package com.example.farm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employee_marks")
public class EmployeeMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long markId;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    //@Min(1)
    //@Max(5)
    @Column(name = "mark")
    private short mark;

    public EmployeeMark(Date date, Employee employee, short mark) {
        this.date = date;
        this.employee = employee;
        this.mark = mark;
    }
}
