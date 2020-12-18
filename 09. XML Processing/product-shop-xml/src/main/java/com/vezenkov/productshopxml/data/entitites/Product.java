package com.vezenkov.productshopxml.data.entitites;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @NotNull(message = "Product name must not be null.")
    @Length(min = 3, message = "Product name must be at least 3 characters.")
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @NotNull(message = "Product price must not be null.")
    @Positive(message = "Product price must be positive")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "user_id")
    private User buyer;

    @ToString.Exclude
    @NonNull
    @NotNull(message = "Seller must not be null.")
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
    private User seller;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = {REFRESH, MERGE})
    @JoinTable(name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"))
    private Set<Category> categories;
}
