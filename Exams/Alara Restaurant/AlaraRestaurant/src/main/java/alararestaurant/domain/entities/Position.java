package alararestaurant.domain.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity{
    private String name;
    private Set<Employee> employees;

    public Position() {
    }

    @NotNull
    @Length(min = 3, max = 30)
    @Column(name = "name", nullable = false, length = 30, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "position")
    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
