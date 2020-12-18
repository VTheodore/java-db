package com.vezenkov.cardealer.services.impl;

import com.vezenkov.cardealer.model.dtos.suppliers.export.SupplierIdAndNameDto;
import com.vezenkov.cardealer.model.dtos.suppliers.export.SupplierIdAndNameRootDto;
import com.vezenkov.cardealer.model.dtos.suppliers.seed.SupplierSeedRootDto;
import com.vezenkov.cardealer.model.entitites.Supplier;
import com.vezenkov.cardealer.repositories.SupplierRepository;
import com.vezenkov.cardealer.services.SupplierService;
import com.vezenkov.cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
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
    public void seedSuppliers(SupplierSeedRootDto supplierSeedRootDto) {
        supplierSeedRootDto.getSuppliers()
                .forEach(supplierDto -> {
                    if (this.validationUtil.isValid(supplierDto)) {
                        if (this.supplierRepository.findSupplierByName(supplierDto.getName()) == null) {
                            Supplier supplier = this.modelMapper.map(supplierDto, Supplier.class);
                            this.supplierRepository.save(supplier);
                        } else {
                            System.out.println("Supplier already in DB.");
                        }
                    } else {
                        this.validationUtil
                                .getViolations(supplierDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Supplier getRandomSupplier() {
        Random random = new Random();
        long supplierId = random.nextInt((int)this.supplierRepository.count()) + 1;
        return this.supplierRepository.findSupplierById(supplierId);
    }

    @Override
    public SupplierIdAndNameRootDto getLocalSuppliers() {
        SupplierIdAndNameRootDto supplierIdAndNameRootDto = new SupplierIdAndNameRootDto();

        supplierIdAndNameRootDto.setSuppliers(this.supplierRepository.findAllByImportedFalse()
                .stream()
                .map(supplier -> {
                    SupplierIdAndNameDto supplierIdAndNameDto = this.modelMapper.map(supplier, SupplierIdAndNameDto.class);
                    supplierIdAndNameDto.setPartsCount(supplier.getParts().size());

                    return supplierIdAndNameDto;
                }).collect(Collectors.toList()));

        return supplierIdAndNameRootDto;
    }
}
