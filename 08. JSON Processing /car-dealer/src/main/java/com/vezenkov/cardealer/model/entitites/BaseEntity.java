package com.vezenkov.cardealer.model.entitites;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
