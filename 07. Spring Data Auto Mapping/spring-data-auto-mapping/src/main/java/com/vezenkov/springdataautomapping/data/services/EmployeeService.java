package com.vezenkov.springdataautomapping.data.services;

import com.vezenkov.springdataautomapping.data.dtos.EmployeeDto;

public interface EmployeeService {
    EmployeeDto getEmployeeByFullName(String name);
}
