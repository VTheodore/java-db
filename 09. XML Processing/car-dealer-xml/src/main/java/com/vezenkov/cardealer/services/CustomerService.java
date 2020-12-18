package com.vezenkov.cardealer.services;


import com.vezenkov.cardealer.model.dtos.customers.export.CustomerFullNameRootDto;
import com.vezenkov.cardealer.model.dtos.customers.export.CustomerOrderRootDto;
import com.vezenkov.cardealer.model.dtos.customers.seed.CustomerSeedRootDto;
import com.vezenkov.cardealer.model.entitites.Customer;

import java.util.Random;

public interface CustomerService {

    void seedCustomers(CustomerSeedRootDto customerSeedRootDto);

    Customer getRandomCustomer(Random random);

    CustomerOrderRootDto getOrderedCustomers();

    CustomerFullNameRootDto getCustomersAndBoughtCars();
}
