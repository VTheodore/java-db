package com.vezenkov.gamestore.services.impl;

import com.vezenkov.gamestore.domain.dtos.GameDetailDto;
import com.vezenkov.gamestore.domain.dtos.GameTitleDto;
import com.vezenkov.gamestore.domain.dtos.GameRegisterDto;
import com.vezenkov.gamestore.domain.dtos.GameTitleAndPriceDto;
import com.vezenkov.gamestore.domain.entities.Game;
import com.vezenkov.gamestore.repositories.GameRepository;
import com.vezenkov.gamestore.services.GameService;
import com.vezenkov.gamestore.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean registerGame(GameRegisterDto gameRegisterDto) {
        if (this.validationUtil.isValid(gameRegisterDto)) {
            Game game = this.modelMapper.map(gameRegisterDto, Game.class);
            this.gameRepository.saveAndFlush(game);
            return true;
        }

        this.validationUtil
                .getViolations(gameRegisterDto)
                .stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
        return false;
    }

    @Override
    public GameTitleDto editGame(Long id, String[] editArgs) {
        Game game = this.gameRepository.findGameById(id);

        if(game == null) return null;

        GameRegisterDto gameRegisterDto = this.modelMapper.map(game, GameRegisterDto.class);

        for (String editArg : editArgs) {
            String[] editArgLines = editArg.split("=");

            if (editArgLines.length != 2) continue;
            String fieldName = editArgLines[0];

            switch (fieldName) {
                case "title" -> gameRegisterDto.setTitle(editArgLines[1]);
                case "price" -> gameRegisterDto.setPrice(new BigDecimal(editArgLines[1]));
                case "size" -> gameRegisterDto.setSize(Double.parseDouble(editArgLines[1]));
                case "trailer" -> gameRegisterDto.setTrailer(editArgLines[1]);
                case "thumbnailURL" -> gameRegisterDto.setThumbnailURL(editArgLines[1]);
                case "description" -> gameRegisterDto.setDescription(editArgLines[1]);
                case "releaseDate" -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    gameRegisterDto.setReleaseDate(LocalDate.parse(editArgLines[1], formatter));
                }
            }
        }

        if(this.validationUtil.isValid(gameRegisterDto)) {
            Game editedGame = this.modelMapper.map(gameRegisterDto, Game.class);
            editedGame.setId(id);
            this.gameRepository.save(editedGame);

            return this.modelMapper.map(editedGame, GameTitleDto.class);
        }

        this.validationUtil.getViolations(gameRegisterDto)
                .stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);

        return null;
    }

    @Override
    public GameTitleDto deleteGame(Long id) {
        Game game = this.gameRepository.findGameById(id);
        if (game == null) return null;

        this.gameRepository.deleteGamesById(id);
        return this.modelMapper.map(game, GameTitleDto.class);
    }

    @Override
    public List<GameTitleAndPriceDto> allGames() {
        return this.gameRepository
                .findAll()
                .stream()
                .map(g -> this.modelMapper.map(g, GameTitleAndPriceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GameDetailDto getGameDetailsByTitle(String title) {
        Game game = this.gameRepository.findGameByTitle(title);

        return game == null ? null : this.modelMapper.map(game, GameDetailDto.class);
    }

    @Override
    public Game getGameByTitle(String title) {
        return this.gameRepository.findGameByTitle(title);
    }
}
