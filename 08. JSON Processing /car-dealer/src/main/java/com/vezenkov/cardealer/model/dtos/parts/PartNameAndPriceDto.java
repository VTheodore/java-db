package com.vezenkov.cardealer.model.dtos.parts;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartNameAndPriceDto {
    @Expose
    private String name;

    @Expose
    private BigDecimal price;
}
