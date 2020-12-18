package com.vezenkov.cardealer.services.impl;

import com.vezenkov.cardealer.model.dtos.cars.CarMakeAndModelDto;
import com.vezenkov.cardealer.model.dtos.customers.CustomerCustomDto;
import com.vezenkov.cardealer.model.dtos.customers.CustomerNameDto;
import com.vezenkov.cardealer.model.dtos.customers.CustomerSeedDto;
import com.vezenkov.cardealer.model.entitites.Customer;
import com.vezenkov.cardealer.repositories.CustomerRepository;
import com.vezenkov.cardealer.services.CustomerService;
import com.vezenkov.cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCustomers(CustomerSeedDto[] customerSeedDtos) {
        Arrays.stream(customerSeedDtos)
                .forEach(customerSeedDto -> {
                    if (this.validationUtil.isValid(customerSeedDto)) {
                        Customer customer = this.modelMapper.map(customerSeedDto, Customer.class);
                        this.customerRepository.saveAndFlush(customer);
                    } else {
                        this.validationUtil
                                .getViolations(customerSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Customer getRandomCustomer() {
        Random random = new Random();
        long customerId = random.nextInt((int)this.customerRepository.count()) + 1;
        return this.customerRepository.findCustomerById(customerId);
    }

    @Override
    public List<CustomerNameDto> getOrderedCustomers() {
        return this.customerRepository.findAllOrderByBirthDateAndYoungDriverIsFalse()
                .stream()
                .map(customer -> {
                    CustomerNameDto customerNameDto = this.modelMapper.map(customer, CustomerNameDto.class);
                    customerNameDto.setSales(customer
                            .getSales()
                            .stream()
                            .map(sale -> this.modelMapper.map(sale.getCar(), CarMakeAndModelDto.class))
                            .collect(Collectors.toSet()));
                    return customerNameDto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<CustomerCustomDto> getCustomersWithTotalSales() {
        return this.customerRepository
                .findCustomersTotalSales()
                .stream()
                .map(object -> {
                    CustomerCustomDto customerCustomDto = new CustomerCustomDto();
                    customerCustomDto.setFullName(object[0].toString());
                    customerCustomDto.setBoughtCars(Long.parseLong(object[1].toString()));
                    customerCustomDto.setSpentMoney(new BigDecimal(object[2].toString()));
                    return customerCustomDto;
                }).collect(Collectors.toList());
    }
}
