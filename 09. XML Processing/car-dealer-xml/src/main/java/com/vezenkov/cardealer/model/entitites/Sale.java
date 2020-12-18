package com.vezenkov.cardealer.model.entitites;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Sale extends BaseEntity{
    @PositiveOrZero(message = "Discount cannot be negative.")
    private BigDecimal discount;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NonNull
    @NotNull(message = "Car in a sale cannot be null.")
    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NonNull
    @NotNull(message = "Customer in a sale cannot be null.")
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}
