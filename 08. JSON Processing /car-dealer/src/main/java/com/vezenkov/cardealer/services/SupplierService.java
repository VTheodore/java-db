package com.vezenkov.cardealer.services;

import com.vezenkov.cardealer.model.dtos.supplier.SupplierNameAndCountDto;
import com.vezenkov.cardealer.model.dtos.supplier.SupplierSeedDto;
import com.vezenkov.cardealer.model.entitites.Supplier;

import java.util.List;

public interface SupplierService {
    void seedSuppliers(SupplierSeedDto[] supplierSeedDtos);

    Supplier getRandomSupplier();

    List<SupplierNameAndCountDto> getAllLocalSuppliers();
}
