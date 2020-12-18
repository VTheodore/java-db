package com.vezenkov.productsshop.models.dtos.users;

import com.google.gson.annotations.Expose;
import com.vezenkov.productsshop.models.dtos.products.ProductSoldDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSoldDto {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private List<ProductSoldDto> soldProducts;
}
