package com.vezenkov.gamestore.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "user_id")
    private User buyer;

    @NonNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "orders_ordered_games",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "game_id"))
    private Set<Game> games;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                '}';
    }
}
