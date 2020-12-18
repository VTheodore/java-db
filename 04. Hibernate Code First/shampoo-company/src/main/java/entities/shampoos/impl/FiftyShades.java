package entities.shampoos.impl;

import entities.Label;
import entities.shampoos.Shampoo;
import util.Size;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "FS")
public class FiftyShades extends Shampoo {
    private static final String BRAND = "FiftyShades";

    private static final BigDecimal PRICE = new BigDecimal("6.69");

    private static final Size SIZE = Size.SMALL;

    public FiftyShades() {
    }

    public FiftyShades(Label label) {
        super(BRAND, PRICE, SIZE, label);
    }
}
