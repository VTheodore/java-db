package com.vezenkov.springdataautomapping.data.services;

import com.vezenkov.springdataautomapping.data.dtos.EmployeeDto;
import com.vezenkov.springdataautomapping.data.entities.Employee;
import com.vezenkov.springdataautomapping.data.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDto getEmployeeByFullName(String name) {
        String[] args = name.split("\\s+");
        Employee employee = this.employeeRepository.findEmployeeByFirstNameAndLastName(args[0], args[1]);

        return this.modelMapper.map(employee, EmployeeDto.class);
    }
}
