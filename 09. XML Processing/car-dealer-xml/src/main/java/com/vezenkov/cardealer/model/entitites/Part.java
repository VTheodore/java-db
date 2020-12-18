package com.vezenkov.cardealer.model.entitites;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table(name = "parts")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Part extends BaseEntity{
    @EqualsAndHashCode.Include
    @NonNull
    @NotNull(message = "Part name cannot be null.")
    @Length(min = 3, max = 100, message = "Part name must be between 3 and 100 characters.")
    @Column(nullable = false, unique = true)
    private String name;

    @NonNull
    @NotNull(message = "Part price cannot be null.")
    @Positive(message = "Part price cannot be negative or zero.")
    @Column(nullable = false)
    private BigDecimal price;

    @PositiveOrZero(message = "Part quantity cannot be negative.")
    private int quantity;

    @ToString.Exclude
    @NonNull
    @NotNull(message = "Part supplier cannot be null.")
    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;
}
