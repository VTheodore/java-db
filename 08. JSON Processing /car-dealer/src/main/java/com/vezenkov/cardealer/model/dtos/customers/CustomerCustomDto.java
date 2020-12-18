package com.vezenkov.cardealer.model.dtos.customers;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCustomDto {
    @Expose
    private String fullName;

    @Expose
    private long boughtCars;

    @Expose
    private BigDecimal spentMoney;
}
