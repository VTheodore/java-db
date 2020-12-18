package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dto.employee_card.EmployeeCardSeedDto;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static hiberspring.common.GlobalConstants.*;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {
    private final EmployeeCardRepository employeeCardRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final Gson gson;

    @Autowired
    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.employeeCardRepository = employeeCardRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return this.employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEE_CARDS_FILE_PATH));
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws FileNotFoundException {
        EmployeeCardSeedDto[] employeeCardSeedDtos = this.gson.fromJson(new FileReader(EMPLOYEE_CARDS_FILE_PATH), EmployeeCardSeedDto[].class);

        StringBuilder employeesOutput = new StringBuilder();
        Arrays.stream(employeeCardSeedDtos)
                .forEach(employeeCardSeedDto -> {
                    if (this.validationUtil.isValid(employeeCardSeedDto)) {
                        EmployeeCard employeeCard = this.modelMapper.map(employeeCardSeedDto, EmployeeCard.class);
                        try {
                            this.employeeCardRepository.save(employeeCard);
                            employeesOutput.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, "Employee Card", employeeCard.getNumber()));
                        }catch (Exception e) {
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
    public EmployeeCard getCardByNumber(String number) {
        return this.employeeCardRepository.findEmployeeCardByNumber(number);
    }
}
