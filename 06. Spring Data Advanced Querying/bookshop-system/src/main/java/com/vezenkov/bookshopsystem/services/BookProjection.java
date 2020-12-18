package com.vezenkov.bookshopsystem.services;

import com.vezenkov.bookshopsystem.entitites.AgeRestriction;
import com.vezenkov.bookshopsystem.entitites.EditionType;

import java.math.BigDecimal;

public interface BookProjection {
    String getTitle();

    EditionType getEditionType();

    AgeRestriction getAgeRestriction();

    BigDecimal getPrice();
}
