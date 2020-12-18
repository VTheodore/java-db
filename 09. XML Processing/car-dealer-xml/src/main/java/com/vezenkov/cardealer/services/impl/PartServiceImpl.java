package com.vezenkov.cardealer.services.impl;

import com.vezenkov.cardealer.model.dtos.parts.seed.PartSeedRootDto;
import com.vezenkov.cardealer.model.entitites.Part;
import com.vezenkov.cardealer.repositories.PartRepository;
import com.vezenkov.cardealer.services.PartService;
import com.vezenkov.cardealer.services.SupplierService;
import com.vezenkov.cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


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
    public void seedPart(PartSeedRootDto partSeedRootDto) {
        partSeedRootDto.getParts()
                .forEach(partDto -> {
                    if (this.validationUtil.isValid(partDto)) {
                        if (this.partRepository.findPartByName(partDto.getName()) == null) {
                            Part part = this.modelMapper.map(partDto, Part.class);
                            part.setSupplier(this.supplierService.getRandomSupplier());
                            this.partRepository.save(part);
                        } else {
                            System.out.println("Part already in DB.");
                        }
                    } else {
                        this.validationUtil
                                .getViolations(partDto)
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

        Set<Part> randomParts = new HashSet<>();

        for (int i = 0; i < count; i++) {
            randomParts.add(this.getRandomPart(random));
        }
        return randomParts;
    }

    private Part getRandomPart(Random random) {
        long partId = random.nextInt((int) this.partRepository.count()) + 1;
        return this.partRepository.findPartById(partId);
    }
}
