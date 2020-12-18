package alararestaurant.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity{
    private Order order;
    private Item item;
    private int quantity;

    public OrderItem() {
    }

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @NotNull
    @Positive
    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
