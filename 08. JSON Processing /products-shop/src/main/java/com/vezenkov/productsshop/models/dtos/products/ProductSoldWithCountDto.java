package com.vezenkov.productsshop.models.dtos.products;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSoldWithCountDto {
    @Expose
    private long count;

    @Expose
    private List<ProductNameAndPriceDto> products;
}
