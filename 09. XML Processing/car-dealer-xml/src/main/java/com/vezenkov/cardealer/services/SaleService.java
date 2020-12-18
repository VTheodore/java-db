package com.vezenkov.cardealer.services;

import com.vezenkov.cardealer.model.dtos.sales.export.SaleRootDto;

public interface SaleService {
    void seedSales();

    SaleRootDto getSalesWithDiscounts();
}
