package com.vezenkov.productsshop.models.dtos.users;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithCountDto {
    @Expose
    private long count;

    @Expose
    private List<UserProductsDto> users;
}
