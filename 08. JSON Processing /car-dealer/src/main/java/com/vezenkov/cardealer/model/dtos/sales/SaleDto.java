package com.vezenkov.cardealer.model.dtos.sales;

import com.google.gson.annotations.Expose;
import com.vezenkov.cardealer.model.dtos.cars.CarBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {
    @Expose
    private CarBasicDto car;

    @Expose
    private String customerName;

    @Expose
    private BigDecimal discount;

    @Expose
    private BigDecimal price;

    @Expose
    private BigDecimal priceWithDiscount;
}
