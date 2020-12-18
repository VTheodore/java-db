package hiberspring.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employee_cards")
public class EmployeeCard extends BaseEntity{
    private String number;

    public EmployeeCard() {
    }

    @NotNull
    @Column(name = "number", unique = true, nullable = false)
    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
