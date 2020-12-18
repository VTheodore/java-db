package com.vezenkov.gamestore.controllers;

import com.vezenkov.gamestore.domain.dtos.*;
import com.vezenkov.gamestore.services.GameService;
import com.vezenkov.gamestore.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.vezenkov.gamestore.util.OperationConstants.*;

@Component
public class AppController implements CommandLineRunner {
    private final UserService userService;

    private final BufferedReader bufferedReader;

    private final GameService gameService;

    public AppController(UserService userService, BufferedReader bufferedReader, GameService gameService) {
        this.userService = userService;
        this.bufferedReader = bufferedReader;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            System.out.println(READ_LINE_ARGS_MSG);
            String[] lineArgs = this.bufferedReader.readLine().split(SPLITTER_REGEXP);
            String operation = lineArgs[0];

            if (operation.toLowerCase().equals(EXIT)) break;

            switch (operation) {
                case REGISTER_USER -> {
                    if (lineArgs.length != REGISTER_USER_LINE_ARGS) {
                        System.out.printf(INVALID_ARGS_MSG, REGISTER_USER_LINE_ARGS, lineArgs.length);
                        break;
                    }

                    if (!lineArgs[2].equals(lineArgs[3])) {
                        System.out.println(PASSWORDS_MISMATCH_MSG);
                        break;
                    }
                    UserRegisterDto userRegisterDto = new UserRegisterDto(lineArgs[1], lineArgs[2], lineArgs[4]);

                    if (this.userService.registerUser(userRegisterDto)) {
                        System.out.printf(SUCCESSFULLY_REGISTERED_USER_MSG, userRegisterDto.getFullName());
                    }
                }
                case LOGIN_USER -> {
                    if (lineArgs.length != LOGIN_USER_LINE_ARGS) {
                        System.out.printf(INVALID_ARGS_MSG, LOGIN_USER_LINE_ARGS, lineArgs.length);
                        break;
                    }
                    UserLoginDto userLoginDto = new UserLoginDto(lineArgs[1], lineArgs[2]);

                    UserNameDto userNameDto = this.userService.loginUser(userLoginDto);

                    if (userNameDto != null) {
                        System.out.printf(SUCCESSFULLY_LOGGED_ID, userNameDto.getFullName());
                    } else {
                        System.out.println(INCORRECT_CREDENTIALS);
                    }
                }
                case LOGOUT -> {
                    UserNameDto userNameDto = this.userService.logout();
                    if (userNameDto != null) {
                        System.out.printf(SUCCESSFULLY_LOGOUT_MSG, userNameDto.getFullName());
                    } else {
                        System.out.println(UNSUCCESSFUL_LOGOUT_MSG);
                    }
                }
                case ADD_GAME -> {
                    if (lineArgs.length != ADD_GAME_LINE_ARGS) {
                        System.out.printf(INVALID_ARGS_MSG, LOGIN_USER_LINE_ARGS, lineArgs.length);
                        break;
                    }
                    if (!this.userService.isLoggedInUserAdmin()) {
                        System.out.println(NOT_ADMIN_ERR);
                        continue;
                    }

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    GameRegisterDto gameRegisterDto = new GameRegisterDto
                            (lineArgs[1], // title
                                    new BigDecimal(lineArgs[2]), // price
                                    Double.parseDouble(lineArgs[3]), // size
                                    lineArgs[4], // trailer
                                    lineArgs[5], // thumbnail URL
                                    lineArgs[6], // Description
                                    LocalDate.parse(lineArgs[7], formatter)); // Release Date

                    if (this.gameService.registerGame(gameRegisterDto)) {
                        System.out.printf(SUCCESSFULLY_ADDED_GAME, gameRegisterDto.getTitle());
                    }
                }
                case EDIT_GAME -> {
                    if (!this.userService.isLoggedInUserAdmin()) {
                        System.out.println(NOT_ADMIN_ERR);
                        continue;
                    }

                    if (lineArgs.length <= MIN_EDIT_GAME_ARGS) {
                        System.out.printf(INVALID_ARGS_MSG, MIN_EDIT_GAME_ARGS, lineArgs.length);
                        continue;
                    }

                    Long id = Long.parseLong(lineArgs[1]);
                    String[] editArgs = Arrays.copyOfRange(lineArgs, 2, lineArgs.length);

                    GameTitleDto gameTitleDto = this.gameService.editGame(id, editArgs);

                    if (gameTitleDto != null) {
                        System.out.printf(GAME_EDITED_SUCCESSFULLY, gameTitleDto.getTitle());
                    } else {
                        System.out.println(GAME_NOT_EDITED);
                    }
                }
                case DELETE_GAME -> {
                    if (!this.userService.isLoggedInUserAdmin()) {
                        System.out.println(NOT_ADMIN_ERR);
                        continue;
                    }

                    if (lineArgs.length != DELETE_GAME_ARGS) {
                        System.out.printf(INVALID_ARGS_MSG, DELETE_GAME_ARGS, lineArgs.length);
                        continue;
                    }

                    Long id = Long.parseLong(lineArgs[1]);
                    GameTitleDto gameTitleDto = this.gameService.deleteGame(id);

                    if (gameTitleDto != null) {
                        System.out.printf(SUCCESSFULLY_DELETED, gameTitleDto.getTitle());
                    }
                }
                case ALL_GAMES -> {
                    if (lineArgs.length != ALL_GAMES_ARGS) {
                        System.out.printf(INVALID_ARGS_MSG, ALL_GAMES_ARGS, lineArgs.length);
                        continue;
                    }

                    this.gameService.allGames()
                            .forEach(g -> System.out.printf("%s %.2f%n", g.getTitle(), g.getPrice()));
                }
                case DETAIL_GAME -> {
                    if (lineArgs.length != DETAIL_GAME_ARGS) {
                        System.out.printf(INVALID_ARGS_MSG, DETAIL_GAME_ARGS, lineArgs.length);
                        continue;
                    }

                    String title = lineArgs[1];

                    GameDetailDto gameDetailDto = this.gameService.getGameDetailsByTitle(title);

                    if (gameDetailDto != null) {
                        System.out.printf("Title: %s%nPrice: %.2f%nDescription: %s%nRelease date: %s%n",
                                gameDetailDto.getTitle(),
                                gameDetailDto.getPrice(),
                                gameDetailDto.getDescription(),
                                gameDetailDto.getReleaseDate().toString());
                    } else {
                        System.out.println(NO_SUCH_GAME);
                    }
                }
                case OWNED_GAMES -> {
                    List<GameTitleDto> ownedGames = this.userService.getOwnedGamesOfLoggedUser();

                    if (ownedGames == null || ownedGames.size() == 0) {
                        System.out.println(NO_OWNED_GAMES);
                    } else {
                        ownedGames
                            .stream()
                            .map(GameTitleDto::getTitle)
                            .forEach(System.out::println);
                    }
                }
                case ADD_ITEM -> {
                    if (lineArgs.length != ADD_ITEM_ARGS) {
                        System.out.printf(INVALID_ARGS_MSG, ADD_ITEM_ARGS, lineArgs.length);
                        continue;
                    }

                    String gameTitle = lineArgs[1];

                    if (this.userService.addGameToLoggedUserShoppingCart(gameTitle)) {
                        System.out.printf(SUCCESSFULLY_ADDED_GAME_TO_CART, gameTitle);
                    } else {
                        System.out.println(UNSUCCESSFULLY_ADDED_OR_REMOVED_GAME_TO_CART);
                    }
                }
                case REMOVE_ITEM -> {
                    if (lineArgs.length != REMOVE_ITEM_ARGS) {
                        System.out.printf(INVALID_ARGS_MSG, REMOVE_ITEM_ARGS, lineArgs.length);
                        continue;
                    }

                    String title = lineArgs[1];

                    if (this.userService.removeGameFromShoppingCart(title)) {
                        System.out.printf(SUCCESSFULLY_REMOVED_GAME_FROM_CART, title);
                    } else {
                        System.out.println(UNSUCCESSFULLY_ADDED_OR_REMOVED_GAME_TO_CART);
                    }
                }
                case BUY_ITEM -> {
                    Set<GameTitleDto> gameTitleDtos = this.userService.buyItemsFromCart();

                    if (gameTitleDtos != null) {
                        System.out.println(SUCCESSFULLY_BOUGHT_GAMES);
                        gameTitleDtos
                                .stream()
                                .map(GameTitleDto::getTitle)
                                .forEach(System.out::println);
                    } else {
                        System.out.println(ERROR_DURING_PAYMENT);
                    }
                }
            }
        }
    }
}
