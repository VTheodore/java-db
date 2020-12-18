package com.vezenkov.springdataadvancedquerying.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "shampoos")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Shampoo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String brand;

    @NonNull
    private BigDecimal price;

    @NonNull
    @Enumerated(value = EnumType.ORDINAL)
    private Size size;

    @NonNull
    @ManyToOne(targetEntity = Label.class)
    @JoinColumn(name = "label_id", referencedColumnName = "id")
    private Label label;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shampoos_ingredients", joinColumns = @JoinColumn(name = "shampoo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    private Set<Ingredient> ingredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shampoo shampoo = (Shampoo) o;
        return id.equals(shampoo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Shampoo{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                '}';
    }
}
