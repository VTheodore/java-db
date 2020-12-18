package com.vezenkov.productsshop.models.dtos.categories;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySeedDto {
    @Expose
    @NotNull(message = "Category name must not be null.")
    @Length(min = 3, max = 15, message = "Category name must be between 3 and 15 characters.")
    private String name;
}
