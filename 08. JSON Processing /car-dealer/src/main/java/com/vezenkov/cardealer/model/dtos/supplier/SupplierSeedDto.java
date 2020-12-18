package com.vezenkov.cardealer.model.dtos.supplier;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierSeedDto {
    @Expose
    @NotNull(message = "Suppliers' name cannot be null.")
    @Length(min = 3, max = 100, message = "Suppliers' name must be between 3 and 100 characters.")
    private String name;

    @Expose
    private boolean isImported;
}
