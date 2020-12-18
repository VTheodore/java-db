package com.vezenkov.usersystem.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "towns")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Town {
    @Id
    @Column(name = "town_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ManyToOne(targetEntity = Country.class)
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private Country country;
}
