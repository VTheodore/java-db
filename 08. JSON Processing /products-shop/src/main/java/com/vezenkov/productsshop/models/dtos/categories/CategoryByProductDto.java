package com.vezenkov.productsshop.models.dtos.categories;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryByProductDto {
    @Expose
    private String name;

    @Expose
    private long productsCount;

    @Expose
    private BigDecimal averagePrice;

    @Expose
    private BigDecimal totalRevenue;
}
