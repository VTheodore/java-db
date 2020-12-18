package com.vezenkov.productsshop.models.dtos.products;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInRangeDto {
    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    @Expose
    private String seller;
}
