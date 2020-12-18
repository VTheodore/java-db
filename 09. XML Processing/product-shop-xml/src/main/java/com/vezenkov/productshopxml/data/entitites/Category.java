package com.vezenkov.productshopxml.data.entitites;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "Category name must not be null.")
    @Length(min = 3, max = 15, message = "Category name must be between 3 and 15 characters.")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY, cascade = {REFRESH, REMOVE, MERGE})
    private Set<Product> products;
}
