package softuni.exam.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.team.TeamNameAndPictureDto;
import softuni.exam.domain.dto.team.TeamSeedRootDto;
import softuni.exam.domain.entity.Picture;
import softuni.exam.domain.entity.Team;
import softuni.exam.repository.TeamRepository;
import softuni.exam.service.PictureService;
import softuni.exam.service.TeamService;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.util.GlobalConstants.TEAMS_FILE_PATH;


@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    private final ValidatorUtil validatorUtil;

    private final ModelMapper modelMapper;

    private final XmlParser xmlParser;

    private final PictureService pictureService;

    private boolean areImported;

    @PostConstruct
    private void init() {
        areImported = this.teamRepository.count() > 0;
    }

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, XmlParser xmlParser, PictureService pictureService) {
        this.teamRepository = teamRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.pictureService = pictureService;
    }

    @Override

    public String importTeams() throws JAXBException, FileNotFoundException {
        TeamSeedRootDto teamSeedRootDto = this.xmlParser.importFromFile(TeamSeedRootDto.class, TEAMS_FILE_PATH);

        StringBuilder sb = new StringBuilder();

        teamSeedRootDto
                .getTeams()
                .forEach(teamSeedDto -> {
                    if (this.validatorUtil.isValid(teamSeedDto)) {
                        if (this.validatorUtil.isValid(teamSeedDto.getPicture())) {
                            if (this.teamRepository.findTeamByNameAndPictureUrl(teamSeedDto.getName(), teamSeedDto.getPicture().getUrl()) != null) {
                                sb.append("Team already in DB.").append(System.lineSeparator());
                                return;
                            }

                            Team team = this.modelMapper.map(teamSeedDto, Team.class);
                            Picture picture = this.pictureService.getPictureByUrl(teamSeedDto.getPicture().getUrl());

                            if (picture == null) {
                                picture = this.pictureService.save(teamSeedDto.getPicture());
                            }

                            team.setPicture(picture);
                            this.teamRepository.save(team);
                            sb.append(String.format("Successfully imported - %s%n", team.getName()));
                        } else {
                            this.validatorUtil
                                    .getViolations(teamSeedDto.getPicture())
                                    .forEach(violation -> sb.append(String.format("Invalid picture. %s%n", violation.getMessage())));
                        }
                    } else {
                        this.validatorUtil
                                .getViolations(teamSeedDto)
                                .forEach(violation -> sb.append(String.format("Invalid team. %s%n", violation.getMessage())));
                    }
                });
        this.areImported = true;
        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.areImported;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE_PATH));
    }

    @Override
    public Team save(TeamNameAndPictureDto teamNameAndPictureDto) {
        if (!this.validatorUtil.isValid(teamNameAndPictureDto)) {
            this.validatorUtil
                    .getViolations(teamNameAndPictureDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }

        return this.teamRepository.save(this.modelMapper.map(teamNameAndPictureDto, Team.class));
    }

    @Override
    public Team save(Team team) {
        return this.teamRepository.save(team);
    }

    @Override
    public Team getTeamByNameAndPictureUrl(String name, String url) {
        return this.teamRepository.findTeamByNameAndPictureUrl(name, url);
    }

}
