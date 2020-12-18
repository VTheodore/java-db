package entities.ingredients;

import entities.shampoos.Shampoo;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ingredient_type", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class Ingredient {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToMany(mappedBy = "ingredients", cascade = CascadeType.ALL)
    private List<Shampoo> shampoos;

    protected Ingredient() {
        this.shampoos = new ArrayList<>();
    }

    protected Ingredient(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
