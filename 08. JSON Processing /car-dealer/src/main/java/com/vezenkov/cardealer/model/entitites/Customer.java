package com.vezenkov.cardealer.model.entitites;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer{
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull(message = "Customers' name cannot be null.")
    @Length(min = 3, max = 60, message = "Length must be between 3 and 60 characters.")
    @Column(nullable = false)
    private String name;

    @NonNull
    @NotNull(message = "Customers' birth date cannot be null.")
    @Past
    @Column(name = "birth_date")
    private Date birthDate;

    @NonNull
    @NotNull(message = "Customers' info whether he is a young driver cannot be null.")
    @Column(name = "is_young_driver")
    private boolean isYoungDriver;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {MERGE, REFRESH})
    private Set<Sale> sales;
}
