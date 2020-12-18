package com.vezenkov.gamestore.services;

import com.vezenkov.gamestore.domain.dtos.GameDetailDto;
import com.vezenkov.gamestore.domain.dtos.GameTitleDto;
import com.vezenkov.gamestore.domain.dtos.GameRegisterDto;
import com.vezenkov.gamestore.domain.dtos.GameTitleAndPriceDto;
import com.vezenkov.gamestore.domain.entities.Game;

import java.util.List;

public interface GameService {
    boolean registerGame(GameRegisterDto gameRegisterDto);

    GameTitleDto editGame(Long id, String[] editArgs);

    GameTitleDto deleteGame(Long id);

    List<GameTitleAndPriceDto> allGames();

    GameDetailDto getGameDetailsByTitle(String title);

    Game getGameByTitle(String title);
}
