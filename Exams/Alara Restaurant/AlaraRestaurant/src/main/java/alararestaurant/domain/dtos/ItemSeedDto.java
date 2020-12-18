package alararestaurant.domain.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ItemSeedDto {
    @Expose
    @NotNull
    @Length(min = 3, max = 30)
    private String name;

    @Expose
    @NotNull
    @Positive
    private BigDecimal price;

    @Expose
    @NotNull
    @Length(min = 3, max = 30)
    private String category;

    public ItemSeedDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String categoryName) {
        this.category = categoryName;
    }
}
