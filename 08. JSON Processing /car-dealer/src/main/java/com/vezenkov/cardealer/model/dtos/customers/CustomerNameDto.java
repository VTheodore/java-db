package com.vezenkov.cardealer.model.dtos.customers;

import com.google.gson.annotations.Expose;
import com.vezenkov.cardealer.model.dtos.cars.CarMakeAndModelDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerNameDto {
    @Expose
    private Long id;

    @Expose
    private String name;

    @Expose
    private boolean isYoungDriver;

    @Expose
    private Set<CarMakeAndModelDto> sales;
}
