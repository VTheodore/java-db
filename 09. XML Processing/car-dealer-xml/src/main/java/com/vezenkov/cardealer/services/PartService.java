package com.vezenkov.cardealer.services;

import com.vezenkov.cardealer.model.dtos.parts.seed.PartSeedRootDto;
import com.vezenkov.cardealer.model.entitites.Part;

import java.util.Set;

public interface PartService {
    void seedPart(PartSeedRootDto partSeedRootDto);

    Set<Part> getRandomParts();
}
