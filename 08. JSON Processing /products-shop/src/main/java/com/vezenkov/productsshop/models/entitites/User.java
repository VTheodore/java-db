package com.vezenkov.productsshop.models.entitites;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Length(max = 60, message = "First name must be max 60 characters long.")
    @Column(name = "first_name", length = 60)
    private String firstName;

    @NonNull
    @NotNull(message = "User last name must not be null.")
    @Length(min = 3, max = 60, message = "User last name must be between 3 and 60 characters long.")
    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    @PositiveOrZero(message = "Age must be positive or zero.")
    @Column(name = "age")
    private int age;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = {MERGE, REMOVE, REFRESH})
    @JoinTable(name = "user_friends",
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "user_id"))
    private Set<User> friends;

    @ToString.Exclude
    @OneToMany(mappedBy = "seller", cascade = ALL, fetch = FetchType.EAGER)
    private Set<Product> productsToSell;

    @ToString.Exclude
    @OneToMany(mappedBy = "buyer", cascade = {MERGE, REFRESH}, fetch = FetchType.LAZY)
    private Set<Product> productsBought;
}
