package com.vezenkov.cardealer.services;

import com.vezenkov.cardealer.model.dtos.customers.CustomerCustomDto;
import com.vezenkov.cardealer.model.dtos.customers.CustomerNameDto;
import com.vezenkov.cardealer.model.dtos.customers.CustomerSeedDto;
import com.vezenkov.cardealer.model.entitites.Customer;

import java.util.List;

public interface CustomerService {
    void seedCustomers(CustomerSeedDto[] customerSeedDtos);

    Customer getRandomCustomer();

    List<CustomerNameDto> getOrderedCustomers();

    List<CustomerCustomDto> getCustomersWithTotalSales();
}
