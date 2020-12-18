package hiberspring.domain.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{
    private String firstName;

    private String lastName;

    private String position;

    private EmployeeCard card;

    private Branch branch;

    public Employee() {
    }

    @NotNull
    @Length(min = 2, max = 60)
    @Column(name = "first_name", nullable = false, length = 60)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @Length(min = 2, max = 60)
    @Column(name = "last_name", nullable = false, length = 60)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    @Length(min = 2, max = 100)
    @Column(name = "position", nullable = false, length = 100)
    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    public EmployeeCard getCard() {
        return this.card;
    }

    public void setCard(EmployeeCard card) {
        this.card = card;
    }

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
