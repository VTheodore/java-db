package entities.ingredients.impl;

import entities.ingredients.Ingredient;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("ST")
public class Strawberry extends Ingredient {
    private static final String NAME = "Strawberry";

    private static final BigDecimal PRICE = new BigDecimal("4.85");

    public Strawberry() {
        super(NAME, PRICE);
    }
}
