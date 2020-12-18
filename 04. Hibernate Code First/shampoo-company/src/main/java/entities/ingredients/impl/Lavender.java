package entities.ingredients.impl;

import entities.ingredients.Ingredient;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("LV")
public class Lavender extends Ingredient {
    private static final String NAME = "Lavender";

    private static final BigDecimal PRICE = new BigDecimal("2.0");

    public Lavender() {
        super(NAME, PRICE);
    }
}