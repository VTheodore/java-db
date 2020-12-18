package entities.ingredients.impl;

import entities.ingredients.Ingredient;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("MT")
public class Mint extends Ingredient {
    private static final String NAME = "Mint";

    private static final BigDecimal PRICE = new BigDecimal("3.54");

    public Mint() {
        super(NAME, PRICE);
    }
}