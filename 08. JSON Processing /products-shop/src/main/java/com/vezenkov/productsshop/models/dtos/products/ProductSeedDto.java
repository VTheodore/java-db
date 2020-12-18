package com.vezenkov.productsshop.models.dtos.products;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSeedDto {
    @Expose
    @NotNull(message = "Product name must not be null.")
    @Length(min = 3, message = "Product name must be at least 3 characters.")
    private String name;

    @Expose
    @NotNull(message = "Product price must not be null.")
    @Positive(message = "Product price must be positive")
    private BigDecimal price;
}
