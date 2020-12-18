package com.vezenkov.cardealer.services;

import com.vezenkov.cardealer.model.dtos.parts.PartSeedDto;
import com.vezenkov.cardealer.model.entitites.Part;

import java.util.Set;

public interface PartService {
    void seedParts(PartSeedDto[] partSeedDtos);

    Set<Part> getRandomParts();
}
