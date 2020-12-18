package com.vezenkov.springdataautomapping.data.repositories;

import com.vezenkov.springdataautomapping.data.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByFirstNameAndLastName(String firsName, String lastName);
}
