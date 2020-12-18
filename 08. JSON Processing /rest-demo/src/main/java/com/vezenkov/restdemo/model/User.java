package com.vezenkov.restdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Expose
    @NonNull
    @Length(min = 2, max = 60)
    @Column(name = "first_name")
    private String firstName;

    @Expose
    @NonNull
    @Length(min = 2, max = 60)
    @Column(name = "last_name")
    private String lastName;

    @Expose
    @NonNull
    @NotNull
    @Length(min = 3, max = 60)
    @Column(name = "username", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private String username;

    @Expose(serialize = false)
    @NonNull
    @Length(min = 3, max = 80)
    @NotNull
    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Expose
    @NonNull
    @NotNull
    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "active")
    private boolean active = true;

    @Expose
    @Length(min = 8, max = 512)
    @Column(name = "image_url")
    @URL
    private String imageUrl;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    @JsonIgnore
    private Collection<Post> posts = new ArrayList<>();

    @Expose
    private Date created = new Date();

    @Expose
    private Date modified = new Date();
}
