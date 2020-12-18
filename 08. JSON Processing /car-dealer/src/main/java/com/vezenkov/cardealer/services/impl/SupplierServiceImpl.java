package com.vezenkov.cardealer.services.impl;

import com.vezenkov.cardealer.model.dtos.supplier.SupplierNameAndCountDto;
import com.vezenkov.cardealer.model.dtos.supplier.SupplierSeedDto;
import com.vezenkov.cardealer.model.entitites.Supplier;
import com.vezenkov.cardealer.repositories.SupplierRepository;
import com.vezenkov.cardealer.services.SupplierService;
import com.vezenkov.cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedSuppliers(SupplierSeedDto[] supplierSeedDtos) {
        Arrays.stream(supplierSeedDtos)
                .forEach(supplierSeedDto -> {
                    if (this.validationUtil.isValid(supplierSeedDto)){
                        if (this.supplierRepository.findSupplierByName(supplierSeedDto.getName()) == null) {
                            Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
                            this.supplierRepository.saveAndFlush(supplier);
                        }
                    } else {
                        this.validationUtil
                                .getViolations(supplierSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Supplier getRandomSupplier() {
        Random random = new Random();
        long supplierId = random.nextInt((int) this.supplierRepository.count()) + 1;
        return this.supplierRepository.findSupplierById(supplierId);
    }

    @Override
    public List<SupplierNameAndCountDto> getAllLocalSuppliers() {
        return this.supplierRepository
                .findAllByImportedFalse()
                .stream()
                .map(supplier -> {
                    SupplierNameAndCountDto supplierNameAndCountDto = this.modelMapper.map(supplier, SupplierNameAndCountDto.class);
                    supplierNameAndCountDto.setPartsCount(supplier.getParts().size());
                    return supplierNameAndCountDto;
                })
                .collect(Collectors.toList());
    }
}
