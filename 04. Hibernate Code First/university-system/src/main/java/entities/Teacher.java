package entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "teachers")
@Data
@EqualsAndHashCode(callSuper = true)
public class Teacher extends Person{
    @Email
    @Column(name = "email")
    private String email;

    @Positive
    @Digits(integer = 5, fraction = 2)
    @Column(name = "salary_per_hour")
    private BigDecimal salaryPerHour;

    public Teacher() {
    }

    public Teacher(@NotNull @NonNull String firstName, @NotNull @NonNull String lastName, String email, BigDecimal salaryPerHour) {
        super(firstName, lastName);
        this.email = email;
        this.salaryPerHour = salaryPerHour;
    }
}
