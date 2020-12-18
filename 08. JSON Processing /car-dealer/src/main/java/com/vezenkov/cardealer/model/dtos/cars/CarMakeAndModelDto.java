package com.vezenkov.cardealer.model.dtos.cars;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarMakeAndModelDto {
    @Expose
    private String make;

    @Expose
    private String model;
}
