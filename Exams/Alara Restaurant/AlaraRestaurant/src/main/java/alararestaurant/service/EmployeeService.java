package alararestaurant.service;

import alararestaurant.domain.entities.Employee;

public interface EmployeeService {

    Boolean employeesAreImported();

    String readEmployeesJsonFile();

    String importEmployees(String employees);

    Employee getEmployeeByName(String name);
}
