package hiberspring.service.impl;

import hiberspring.domain.dto.employee.EmployeeSeedRootDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.BranchService;
import hiberspring.service.EmployeeCardService;
import hiberspring.service.EmployeeService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static hiberspring.common.GlobalConstants.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final EmployeeCardService employeeCardService;

    private final BranchService branchService;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final XmlParser xmlParser;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeCardService employeeCardService, BranchService branchService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.employeeRepository = employeeRepository;
        this.employeeCardService = employeeCardService;
        this.branchService = branchService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEES_FILE_PATH));
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {
        EmployeeSeedRootDto employeeSeedRootDto = this.xmlParser.parseXml(EmployeeSeedRootDto.class, EMPLOYEES_FILE_PATH);

        StringBuilder employeesOutput = new StringBuilder();

        employeeSeedRootDto.getEmployees()
                .forEach(employeeSeedDto -> {
                    if (this.validationUtil.isValid(employeeSeedDto)) {
                        EmployeeCard employeeCard = this.employeeCardService.getCardByNumber(employeeSeedDto.getCard());

                        if (employeeCard == null) {
                            employeesOutput.append(INCORRECT_DATA_MESSAGE)
                                    .append(System.lineSeparator());
                            return;
                        }

                        Branch branch = this.branchService.getBranchByName(employeeSeedDto.getBranch());

                        if (branch == null) {
                            employeesOutput.append(INCORRECT_DATA_MESSAGE)
                                    .append(System.lineSeparator());
                        }

                        Employee employee = this.modelMapper.map(employeeSeedDto, Employee.class);
                        employee.setCard(employeeCard);
                        employee.setBranch(branch);

                        try {
                            this.employeeRepository.save(employee);
                            employeesOutput.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, "Employee", employeeSeedDto.getFirstName() + " " + employeeSeedDto.getLastName()));
                        } catch (Exception e) {
                            employeesOutput.append(INCORRECT_DATA_MESSAGE);
                        }
                    } else {
                        employeesOutput.append(INCORRECT_DATA_MESSAGE);
                    }

                    employeesOutput.append(System.lineSeparator());
                });
        return employeesOutput.toString();
    }

    @Override
    public String exportProductiveEmployees() {
        StringBuilder res = new StringBuilder();

        this.employeeRepository.findAllByBranchWithAtLeastOneProduct()
                .forEach(employee -> {
                    res.append(String.format("Name: %s %s%n", employee.getFirstName(), employee.getLastName()));
                    res.append(String.format("Position: %s%n", employee.getPosition().toString()));
                    res.append(String.format("Card Number: %s%n", employee.getCard().getNumber()));
                    res.append("-------------------------");
                    res.append(System.lineSeparator());
                });

        return res.toString();
    }
}
