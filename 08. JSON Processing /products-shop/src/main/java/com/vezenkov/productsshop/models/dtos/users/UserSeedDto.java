package com.vezenkov.productsshop.models.dtos.users;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
public class UserSeedDto {
    @Expose
    @Length(max = 60, message = "First name must be max 60 characters long.")
    private String firstName;

    @Expose
    @NotNull(message = "User last name must not be null.")
    @Length(min = 3, max = 60, message = "User last name must be between 3 and 60 characters long.")
    private String lastName;

    @Expose
    @PositiveOrZero(message = "Age must be positive or zero.")
    private int age;
}
