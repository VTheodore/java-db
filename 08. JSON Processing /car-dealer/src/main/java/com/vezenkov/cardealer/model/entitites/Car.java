package com.vezenkov.cardealer.model.entitites;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Car {
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull(message = "Car make cannot be null.")
    @Length(min = 3, max = 30, message = "Car make must be between 3 and 30 characters")
    @Column(nullable = false)
    private String make;

    @NonNull
    @NotNull(message = "Car model cannot be null.")
    @Length(min = 1, max = 60, message = "Car model must be between 3 and 60 characters.")
    @Column(nullable = false)
    private String model;

    @Column(name = "travelled_distance")
    private String travelledDistance;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cars_parts",
            joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "part_id", referencedColumnName = "id"))
    private Set<Part> parts;
}
