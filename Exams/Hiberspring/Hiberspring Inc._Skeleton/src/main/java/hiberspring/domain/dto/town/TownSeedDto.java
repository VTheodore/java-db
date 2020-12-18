package hiberspring.domain.dto.town;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TownSeedDto {
    @Expose
    @NotNull
    @Length(min = 2, max = 50)
    private String name;

    @Expose
    @NotNull
    @Positive
    private int population;

    public TownSeedDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return this.population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
