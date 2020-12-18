package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dto.town.TownSeedDto;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.service.TownService;
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
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns(String townsFileContent) throws FileNotFoundException {
        TownSeedDto[] townSeedDtos = this.gson.fromJson(new FileReader(TOWNS_FILE_PATH), TownSeedDto[].class);
        StringBuilder townOutput = new StringBuilder();

        Arrays.stream(townSeedDtos)
                .forEach(townSeedDto -> {
                    if (this.validationUtil.isValid(townSeedDto)) {
                        Town town = this.modelMapper.map(townSeedDto, Town.class);
                        this.townRepository.save(town);
                        townOutput.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, "Town", townSeedDto.getName()));
                    } else {
                        townOutput.append(INCORRECT_DATA_MESSAGE);
                    }

                    townOutput.append(System.lineSeparator());
                });
        return townOutput.toString();
    }

    @Override
    public Town getTownByName(String name) {
        return this.townRepository.findTownByName(name);
    }
}
