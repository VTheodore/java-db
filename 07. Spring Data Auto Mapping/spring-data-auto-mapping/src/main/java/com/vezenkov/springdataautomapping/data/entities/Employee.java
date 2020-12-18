package com.vezenkov.springdataautomapping.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    private BigDecimal salary;

    private LocalDate birthday;

    private String address;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Employee manager;
}
