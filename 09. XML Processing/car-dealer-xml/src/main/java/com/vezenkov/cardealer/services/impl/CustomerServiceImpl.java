package com.vezenkov.cardealer.services.impl;

import com.vezenkov.cardealer.model.dtos.customers.export.CustomerFullNameDto;
import com.vezenkov.cardealer.model.dtos.customers.export.CustomerFullNameRootDto;
import com.vezenkov.cardealer.model.dtos.customers.export.CustomerOrderRootDto;
import com.vezenkov.cardealer.model.dtos.customers.export.CustomersOrderedDto;
import com.vezenkov.cardealer.model.dtos.customers.seed.CustomerSeedRootDto;
import com.vezenkov.cardealer.model.entitites.Customer;
import com.vezenkov.cardealer.repositories.CustomerRepository;
import com.vezenkov.cardealer.services.CustomerService;
import com.vezenkov.cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
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
    public void seedCustomers(CustomerSeedRootDto customerSeedRootDto) {
        customerSeedRootDto
                .getCustomers()
                .forEach(customerDto -> {
                    if (this.validationUtil.isValid(customerDto)) {
                        Customer customer = this.modelMapper.map(customerDto, Customer.class);
                        this.customerRepository.save(customer);
                    } else {
                        this.validationUtil
                                .getViolations(customerDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });

    }

    @Override
    public Customer getRandomCustomer(Random random) {
        long customerId = random.nextInt((int) this.customerRepository.count()) + 1;
        return this.customerRepository.findCustomerById(customerId);
    }

    @Override
    public CustomerOrderRootDto getOrderedCustomers() {
        CustomerOrderRootDto customerOrderRootDto = new CustomerOrderRootDto();

        customerOrderRootDto.setCustomers(
                this.customerRepository
                .findAllOrderByBirthDateAndYoungDriverIsFalse()
                .stream()
                .map(customer -> this.modelMapper.map(customer, CustomersOrderedDto.class))
                .collect(Collectors.toList()));
        return customerOrderRootDto;
    }

    @Override
    public CustomerFullNameRootDto getCustomersAndBoughtCars() {
        CustomerFullNameRootDto customerFullNameRootDto = new CustomerFullNameRootDto();

        customerFullNameRootDto.setCustomers(this.customerRepository.findCustomersTotalSales()
                .stream()
                .map(objects -> {
                    CustomerFullNameDto customerFullNameDto = new CustomerFullNameDto();
                    customerFullNameDto.setFullName(objects[0].toString());
                    customerFullNameDto.setBoughtCars(Integer.parseInt(objects[1].toString()));
                    customerFullNameDto.setSpentMoney(new BigDecimal(objects[2].toString()));
                    return customerFullNameDto;
                }).collect(Collectors.toList()));

        return customerFullNameRootDto;
    }
}
