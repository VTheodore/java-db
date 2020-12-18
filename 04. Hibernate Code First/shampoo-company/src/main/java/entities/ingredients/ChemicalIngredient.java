package entities.ingredients;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ChemicalIngredient extends Ingredient {
    @Column(name = "chemical_formula")
    private String chemicalFormula;

    protected ChemicalIngredient() {
    }

    ;

    protected ChemicalIngredient(String name, BigDecimal price, String chemicalFormula) {
        super(name, price);
        this.chemicalFormula = chemicalFormula;
    }
}
