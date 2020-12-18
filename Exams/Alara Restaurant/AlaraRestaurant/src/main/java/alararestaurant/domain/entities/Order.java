package alararestaurant.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    private String customer;
    private Date dateTime;
    private OrderType type = OrderType.ForHere;
    private Employee employee;
    private Set<OrderItem> orderItems;

    public Order() {
    }

    @NotNull
    @Column(name = "customer", nullable = false, updatable = false)
    public String getCustomer() {
        return this.customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @NotNull
    @PastOrPresent
    @Column(name = "date_time", nullable = false, updatable = false)
    public Date getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public OrderType getType() {
        return this.type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @OneToMany(mappedBy = "order", cascade = {REFRESH, MERGE})
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
