package com.vezenkov.cardealer.model.dtos.customers;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSeedDto {
    @Expose
    @NotNull(message = "Customers' name cannot be null.")
    @Length(min = 3, max = 60, message = "Length must be between 3 and 60 characters.")
    private String name;

    @Expose
    @NotNull(message = "Customers' birth date cannot be null.")
    @Past
    private Date birthDate;

    @Expose
    @NotNull(message = "Customers' info whether he is a young driver cannot be null.")
    private boolean isYoungDriver;
}
