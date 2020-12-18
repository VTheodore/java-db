package hiberspring.domain.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity{
    private String name;

    private int population;

    public Town() {
    }

    @NotNull
    @Length(min = 2, max = 50)
    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Positive
    @Column(name = "population", nullable = false)
    public int getPopulation() {
        return this.population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
