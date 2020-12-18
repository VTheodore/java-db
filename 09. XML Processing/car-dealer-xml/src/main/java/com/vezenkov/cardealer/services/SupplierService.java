package com.vezenkov.cardealer.services;

import com.vezenkov.cardealer.model.dtos.suppliers.export.SupplierIdAndNameRootDto;
import com.vezenkov.cardealer.model.dtos.suppliers.seed.SupplierSeedRootDto;
import com.vezenkov.cardealer.model.entitites.Supplier;

public interface SupplierService {
    void seedSuppliers(SupplierSeedRootDto supplierSeedRootDto);

    Supplier getRandomSupplier();

    SupplierIdAndNameRootDto getLocalSuppliers();
}
