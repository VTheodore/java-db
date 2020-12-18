package com.vezenkov.gamestore.services;

import com.vezenkov.gamestore.domain.dtos.*;

import java.util.List;
import java.util.Set;

public interface UserService {
    boolean registerUser(UserRegisterDto userRegisterDto);

    UserNameDto loginUser(UserLoginDto userLoginDto);

    UserNameDto logout();

    boolean isLoggedInUserAdmin();

    List<GameTitleDto> getOwnedGamesOfLoggedUser();

    boolean addGameToLoggedUserShoppingCart(String gameTitle);

    boolean removeGameFromShoppingCart(String gameTitle);

    Set<GameTitleDto> buyItemsFromCart();
}
