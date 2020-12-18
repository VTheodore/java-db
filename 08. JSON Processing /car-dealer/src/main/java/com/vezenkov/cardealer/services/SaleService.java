package com.vezenkov.cardealer.services;

import com.vezenkov.cardealer.model.dtos.sales.SaleDto;

import java.util.List;

public interface SaleService {
    void seedSales();

    List<SaleDto> getSalesWithAppliedDiscount();
}
