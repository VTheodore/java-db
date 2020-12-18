package entities.shampoos.impl;

import entities.Label;
import entities.shampoos.Shampoo;
import util.Size;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "PP")
public class PinkPanther extends Shampoo {
    private static final String BRAND = "Pink Panther";

    private static final BigDecimal PRICE = new BigDecimal("8.50");

    private static final Size SIZE = Size.MEDIUM;

    public PinkPanther() {
    }

    public PinkPanther(Label label) {
        super(BRAND, PRICE, SIZE, label);
    }
}