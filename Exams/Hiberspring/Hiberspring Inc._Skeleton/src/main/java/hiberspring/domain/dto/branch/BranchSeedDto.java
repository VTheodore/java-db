package hiberspring.domain.dto.branch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class BranchSeedDto {
    @Expose
    @NotNull
    private String name;

    @Expose
    @NotNull
    @Length(min = 2, max = 50)
    @SerializedName("town")
    private String townName;

    public BranchSeedDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTownName() {
        return this.townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
