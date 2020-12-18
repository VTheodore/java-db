package com.vezenkov.cardealer.model.dtos.cars;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarSeedDto {
    @Expose
    @NotNull(message = "Car make cannot be null.")
    @Length(min = 3, max = 30, message = "Car make must be between 3 and 30 characters")
    private String make;

    @Expose
    @NotNull(message = "Car model cannot be null.")
    @Length(min = 1, max = 60, message = "Car model must be between 3 and 60 characters.")
    private String model;

    @Expose
    private String travelledDistance;
}
