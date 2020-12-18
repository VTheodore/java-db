package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Customer {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NonNull
    @Column(name = "name")
    private String name;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "credit_card_number")
    private String creditCardNumber;

    @OneToMany(mappedBy = "customer", targetEntity = Sale.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Sale> sales;
}
