package alararestaurant.domain.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EmployeeSeedDto {
    @Expose
    @NotNull
    @Length(min = 3, max = 30)
    private String name;

    @Expose
    @NotNull
    @Min(15)
    @Max(80)
    private byte age;

    @Expose
    @NotNull
    @Length(min = 3, max = 30)
    private String position;

    public EmployeeSeedDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getAge() {
        return this.age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String positionName) {
        this.position = positionName;
    }
}
