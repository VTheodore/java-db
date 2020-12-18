package com.vezenkov.usersystem.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @NotNull
    @Size(min = 4, max = 30)
    @Column(name = "username", length = 30, nullable = false, unique = true)
    private String username;

    @NonNull
    @NotNull
    //add password
    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @NonNull
    @NotNull
    @Email
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Size(min = 2, max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min = 2, max = 60)
    @Column(name = "last_name", length = 60, nullable = false)
    private String lastName;

    @Transient
    private String fullName;

    @NotNull
    @PastOrPresent
    @Column(name = "registered_on")
    private Date registeredOn;

    @PastOrPresent
    @Column(name = "last_time_logged_in")
    private Date lastTimeLoggedIn;

    @Size(min = 1, max = 120)
    @Column(name = "age")
    private byte age;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToOne(targetEntity = Town.class)
    @JoinColumn(name = "born_town_id", referencedColumnName = "town_id")
    private Town bornTown;

    @ManyToOne(targetEntity = Town.class)
    @JoinColumn(name = "currently_living_town_id", referencedColumnName = "town_id")
    private Town currentlyLivingTown;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "friends",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Album> albums = new HashSet<>();

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
