package alararestaurant.domain.entities;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{
    private String name;
    private byte age;
    private Position position;
    private Set<Order> orders;

    public Employee() {
    }

    @NotNull
    @Length(min = 3, max = 30)
    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Min(15)
    @Max(80)
    @Column(name = "age", nullable = false)
    public byte getAge() {
        return this.age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @OneToMany(mappedBy = "employee")
    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
