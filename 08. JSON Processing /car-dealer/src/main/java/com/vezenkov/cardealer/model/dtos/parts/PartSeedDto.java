package com.vezenkov.cardealer.model.dtos.parts;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartSeedDto {
    @Expose
    @NotNull(message = "Part name cannot be null.")
    @Length(min = 3, max = 100, message = "Part name must be between 3 and 100 characters.")
    private String name;

    @Expose
    @NotNull(message = "Part price cannot be null.")
    @Positive(message = "Part price cannot be negative or zero.")
    private BigDecimal price;

    @Expose
    @PositiveOrZero(message = "Part quantity cannot be negative.")
    private int quantity;
}
