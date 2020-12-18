package com.vezenkov.cardealer.model.entitites;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Supplier{
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull(message = "Suppliers' name cannot be null.")
    @Length(min = 3, max = 100, message = "Suppliers' name must be between 3 and 100 characters.")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "is_imported")
    private Boolean isImported;

    @ToString.Exclude
    @OneToMany(cascade = {ALL}, fetch = FetchType.EAGER, mappedBy = "supplier")
    private Set<Part> parts;
}
