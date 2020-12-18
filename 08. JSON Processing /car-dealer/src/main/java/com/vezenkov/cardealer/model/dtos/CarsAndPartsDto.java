package com.vezenkov.cardealer.model.dtos;

import com.google.gson.annotations.Expose;
import com.vezenkov.cardealer.model.dtos.cars.CarBasicDto;
import com.vezenkov.cardealer.model.dtos.parts.PartNameAndPriceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarsAndPartsDto {
    @Expose
    private CarBasicDto car;

    @Expose
    private List<PartNameAndPriceDto> parts;
}
