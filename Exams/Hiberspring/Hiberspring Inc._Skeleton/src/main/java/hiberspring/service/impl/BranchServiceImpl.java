package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dto.branch.BranchSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.service.BranchService;
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
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;

    private final TownService townService;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final Gson gson;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, TownService townService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.branchRepository = branchRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return Files.readString(Path.of(BRANCHES_FILE_PATH));
    }

    @Override
    public String importBranches(String branchesFileContent) throws FileNotFoundException {
        BranchSeedDto[] branchSeedDtos = this.gson.fromJson(new FileReader(BRANCHES_FILE_PATH), BranchSeedDto[].class);
        StringBuilder branchesOutput = new StringBuilder();

        Arrays.stream(branchSeedDtos)
                .forEach(branchSeedDto -> {
                    if (this.validationUtil.isValid(branchSeedDto)) {
                        Town town = this.townService.getTownByName(branchSeedDto.getTownName());
                        if (town == null) {
                            branchesOutput.append(INCORRECT_DATA_MESSAGE)
                                    .append(System.lineSeparator());

                            return;
                        }

                        Branch branch = this.modelMapper.map(branchSeedDto, Branch.class);

                        branch.setTown(town);
                        this.branchRepository.save(branch);

                        branchesOutput.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, "Branch", branchSeedDto.getName()));
                    } else {
                        branchesOutput.append(INCORRECT_DATA_MESSAGE);
                    }

                    branchesOutput.append(System.lineSeparator());
                });

        return branchesOutput.toString();
    }

    @Override
    public Branch getBranchByName(String name) {
        return this.branchRepository.findBranchByName(name);
    }
}
