package com.vezenkov.springdataautomapping.data;

import com.vezenkov.springdataautomapping.data.entities.Employee;
import com.vezenkov.springdataautomapping.data.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {

    private final EmployeeService employeeService;

    @Autowired
    public AppInitializer(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
