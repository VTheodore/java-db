package com.vezenkov.productsshop.models.dtos.users;

import com.google.gson.annotations.Expose;
import com.vezenkov.productsshop.models.dtos.products.ProductSoldWithCountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProductsDto {
    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private int age;

    @Expose
    private ProductSoldWithCountDto soldProducts;
}
