package entities.shampoos;

import entities.Label;
import entities.ingredients.Ingredient;
import lombok.Data;
import util.Size;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shampoos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "shampoo_type", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class Shampoo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String brand;

    @Basic
    private BigDecimal price;

    @Enumerated
    private Size size;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "label", referencedColumnName = "id")
    private Label label;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "shampoos_ingredients",
            joinColumns = @JoinColumn(name = "shampoo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    private Set<Ingredient> ingredients;

    public Shampoo() {
        this.ingredients = new HashSet<>();
    }

    protected Shampoo(String brand, BigDecimal price, Size size, Label label) {
        this.ingredients = new HashSet<>();
        this.brand = brand;
        this.price = price;
        this.size = size;
        this.label = label;
    }
}
