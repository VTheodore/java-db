package alararestaurant.service.impl;

import alararestaurant.domain.dtos.EmployeeSeedDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.service.EmployeeService;
import alararestaurant.service.PositionService;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static alararestaurant.util.GlobalConstants.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final Gson gson;

    private final PositionService positionService;

    private final ModelMapper modelMapper;

    private final FileUtil fileUtil;

    private final ValidationUtil validationUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, Gson gson, PositionService positionService, ModelMapper modelMapper, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.gson = gson;
        this.positionService = positionService;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() {
        return this.fileUtil.readFile(EMPLOYEES_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        EmployeeSeedDto[] employeeSeedDtos = this.gson.fromJson(employees, EmployeeSeedDto[].class);
        StringBuilder employeesOutput = new StringBuilder();

        Arrays.stream(employeeSeedDtos)
                .forEach(employeeSeedDto -> {
                    if (this.validationUtil.isValid(employeeSeedDto)) {
                        Position position = this.positionService.getPositionByName(employeeSeedDto.getPosition());

                        if (position == null) {
                            position = this.positionService.save(employeeSeedDto.getPosition());
                        }

                        Employee employee = this.modelMapper.map(employeeSeedDto, Employee.class);
                        employee.setPosition(position);

                        this.employeeRepository.save(employee);

                        employeesOutput.append(String.format(SUCCESSFULLY_IMPORTED, employeeSeedDto.getName()));
                    } else {
                        employeesOutput.append(ERROR_MESSAGE);
                    }

                    employeesOutput.append(System.lineSeparator());
                });
        return employeesOutput.toString();
    }

    @Override
    public Employee getEmployeeByName(String name) {
        return this.employeeRepository.findEmployeeByName(name);
    }
}
