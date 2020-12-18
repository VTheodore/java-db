package entities.shampoos.impl;

import entities.Label;
import entities.shampoos.Shampoo;
import util.Size;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "FN")
public class FreshNuke extends Shampoo {
    private static final String BRAND = "Fresh Nuke";

    private static final BigDecimal PRICE = new BigDecimal("9.33");

    private static final Size SIZE = Size.LARGE;

    public FreshNuke() {
    }

    public FreshNuke(Label label) {
        super(BRAND, PRICE, SIZE, label);
    }
}
