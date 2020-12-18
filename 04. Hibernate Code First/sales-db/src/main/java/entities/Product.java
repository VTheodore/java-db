package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NonNull
    @Column(name = "name")
    private String name;

    @NotNull
    @NonNull
    @Min(0)
    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @NonNull
    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(mappedBy = "product", targetEntity = Sale.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Sale> sales;
}
