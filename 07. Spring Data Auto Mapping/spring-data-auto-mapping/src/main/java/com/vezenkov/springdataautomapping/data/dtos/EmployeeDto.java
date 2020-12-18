package com.vezenkov.springdataautomapping.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private String firstName;

    private String lastName;

    private BigDecimal salary;
}
