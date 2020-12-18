package softuni.exam.service.impl;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.player.PlayerSeedDto;
import softuni.exam.domain.entity.Picture;
import softuni.exam.domain.entity.Player;
import softuni.exam.domain.entity.Team;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.service.PictureService;
import softuni.exam.service.PlayerService;
import softuni.exam.service.TeamService;
import softuni.exam.util.ValidatorUtil;


import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.util.GlobalConstants.PLAYERS_FILE_PATH;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    private final Gson gson;

    private final ValidatorUtil validatorUtil;

    private final ModelMapper modelMapper;

    private final PictureService pictureService;

    private final TeamService teamService;

    private boolean areImported;

    @PostConstruct
    private void init() {
        this.areImported = this.playerRepository.count() > 0;
    }

    public PlayerServiceImpl(PlayerRepository playerRepository, Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper, PictureService pictureService, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
        this.teamService = teamService;
    }


    @Override
    public String importPlayers() throws FileNotFoundException {
        PlayerSeedDto[] playerSeedDtos = this.gson.fromJson(new FileReader(PLAYERS_FILE_PATH), PlayerSeedDto[].class);
        StringBuilder sb = new StringBuilder();

        Arrays.stream(playerSeedDtos)
                .forEach(playerSeedDto -> {
                    if (this.validatorUtil.isValid(playerSeedDto)) {
                        if (!this.validatorUtil.isValid(playerSeedDto.getTeam())) {
                            this.validatorUtil
                                    .getViolations(playerSeedDto.getTeam())
                                    .forEach(violation -> sb.append(String.format("Invalid player. %s%n", violation.getMessage())));
                            return;
                        }

                        if (!this.validatorUtil.isValid(playerSeedDto.getPicture())) {
                            this.validatorUtil
                                    .getViolations(playerSeedDto.getPicture())
                                    .forEach(violation -> sb.append(String.format("Invalid player. %s%n", violation.getMessage())));
                            return;
                        }

                        Player player = this.modelMapper.map(playerSeedDto, Player.class);
                        Picture picture = this.pictureService.getPictureByUrl(playerSeedDto.getPicture().getUrl());

                        if (picture == null) {
                            picture = this.pictureService.save(playerSeedDto.getPicture());
                        }
                        player.setPicture(picture);

                        Team team = this.teamService.getTeamByNameAndPictureUrl(playerSeedDto.getTeam().getName(), playerSeedDto.getTeam().getPicture().getUrl());

                        if (team == null) {
                            Picture teamPicture = this.pictureService.getPictureByUrl(playerSeedDto.getTeam().getPicture().getUrl());

                            if (teamPicture == null) {
                                teamPicture = this.pictureService.save(playerSeedDto.getTeam().getPicture());
                            }

                            team = this.modelMapper.map(playerSeedDto.getTeam(), Team.class);
                            team.setPicture(teamPicture);

                            team = this.teamService.save(team);
                        }
                        player.setTeam(team);

                        this.playerRepository.save(player);
                        sb.append(String.format("Successfully imported player: %s %s%n", playerSeedDto.getFirstName(), playerSeedDto.getLastName()));
                    } else {
                        this.validatorUtil
                                .getViolations(playerSeedDto)
                                .forEach(violation -> sb.append(String.format("Invalid player. %s%n", violation.getMessage())));
                    }
                });

        areImported = true;
        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.areImported;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();

        this.playerRepository.findPlayerBySalaryGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000))
                .forEach(player -> {
                    sb.append(String.format("Player name: %s %s%n", player.getFirstName(), player.getLastName()));
                    sb.append(String.format("      Number: %d%n", player.getNumber()));
                    sb.append(String.format("      Salary: %.2f%n", player.getSalary()));
                    sb.append(String.format("      Team: %s%n", player.getTeam().getName()));
                });
        return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team: North Hub").append(System.lineSeparator());
        this.playerRepository.findPlayersByTeamNameOrderByIdAsc("North Hub")
                .forEach(player -> {
                    sb.append(String.format("      Player name: %s %s - %s%n", player.getFirstName(), player.getLastName(), player.getPosition().toString()));
                    sb.append(String.format("      Number: %d%n", player.getNumber()));
                });

        return sb.toString();
    }
}
