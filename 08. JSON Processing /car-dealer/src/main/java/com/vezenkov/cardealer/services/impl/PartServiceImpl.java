package com.vezenkov.cardealer.services.impl;

import com.vezenkov.cardealer.model.dtos.parts.PartSeedDto;
import com.vezenkov.cardealer.model.entitites.Part;
import com.vezenkov.cardealer.repositories.PartRepository;
import com.vezenkov.cardealer.services.PartService;
import com.vezenkov.cardealer.services.SupplierService;
import com.vezenkov.cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.*;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final SupplierService supplierService;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidationUtil validationUtil, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.supplierService = supplierService;
    }

    @Override
    public void seedParts(PartSeedDto[] partSeedDtos) {
        Arrays.stream(partSeedDtos)
                .forEach(partSeedDto -> {
                    if (this.validationUtil.isValid(partSeedDto)) {
                        if (this.partRepository.findPartByName(partSeedDto.getName()) == null) {
                            Part part = this.modelMapper.map(partSeedDto, Part.class);
                            part.setSupplier(this.supplierService.getRandomSupplier());
                            this.partRepository.saveAndFlush(part);
                        }
                    } else {
                        this.validationUtil
                                .getViolations(partSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Set<Part> getRandomParts() {
        Random random = new Random();
        int count = random.nextInt(10) + 10;

        Set<Part> parts = new HashSet<>();

        for (int i = 0; i < count; i++) {
            parts.add(this.getRandomCar(random));
        }

        return parts;
    }

    private Part getRandomCar(Random random) {
        long partId = random.nextInt((int) this.partRepository.count()) + 1;
        return this.partRepository.findPartById(partId);
    }
}
