package com.vezenkov.gamestore.services.impl;

import com.vezenkov.gamestore.domain.dtos.*;
import com.vezenkov.gamestore.domain.entities.Game;
import com.vezenkov.gamestore.domain.entities.Role;
import com.vezenkov.gamestore.domain.entities.User;
import com.vezenkov.gamestore.repositories.UserRepository;
import com.vezenkov.gamestore.services.GameService;
import com.vezenkov.gamestore.services.OrderService;
import com.vezenkov.gamestore.services.UserService;
import com.vezenkov.gamestore.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private User currentlyLoggedIn;

    private final GameService gameService;

    private final OrderService orderService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, GameService gameService, OrderService orderService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    @Override
    public boolean registerUser(UserRegisterDto userRegisterDto) {
        if (validationUtil.isValid(userRegisterDto)) {
            User user = this.modelMapper.map(userRegisterDto, User.class);
            user.setRole(this.userRepository.count() == 0 ? Role.ADMIN : Role.USER);

            this.userRepository.saveAndFlush(user);
            return true;
        }

        validationUtil.getViolations(userRegisterDto)
                .stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);

        return false;
    }

    @Override
    public UserNameDto loginUser(UserLoginDto userLoginDto) {
        User user = this.userRepository.findUserByEmail(userLoginDto.getEmail());

        if (user == null || !user.getPassword().equals(userLoginDto.getPassword())) return null;
        this.currentlyLoggedIn = user;
        return this.modelMapper.map(this.currentlyLoggedIn, UserNameDto.class);
    }

    @Override
    public UserNameDto logout() {
        if (this.currentlyLoggedIn == null) return null;

        UserNameDto userNameDto = this.modelMapper.map(this.currentlyLoggedIn, UserNameDto.class);
        this.currentlyLoggedIn = null;

        return userNameDto;
    }

    @Override
    public boolean isLoggedInUserAdmin() {
        return this.currentlyLoggedIn != null && this.currentlyLoggedIn.getRole().equals(Role.ADMIN);
    }

    @Override
    public List<GameTitleDto> getOwnedGamesOfLoggedUser() {
        return this.currentlyLoggedIn == null ? null : this.currentlyLoggedIn
                .getGames()
                .stream()
                .map(g -> this.modelMapper.map(g, GameTitleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addGameToLoggedUserShoppingCart(String gameTitle) {
        if (this.currentlyLoggedIn == null) return false;

        Game game = this.gameService.getGameByTitle(gameTitle);

        if (game == null) return false;

        return !this.currentlyLoggedIn.getGames().contains(game) && this.currentlyLoggedIn.getShoppingCart().add(game);
    }

    @Override
    public boolean removeGameFromShoppingCart(String gameTitle) {
        if (this.currentlyLoggedIn == null) return false;

        Game game = this.gameService.getGameByTitle(gameTitle);

        if (game == null) return false;

        return this.currentlyLoggedIn.getShoppingCart().remove(game);
    }

    @Override
    public Set<GameTitleDto> buyItemsFromCart() {
        if (this.currentlyLoggedIn == null) return null;

        if (this.currentlyLoggedIn.getShoppingCart().size() == 0) return null;

        this.orderService.addOrder(this.currentlyLoggedIn, this.currentlyLoggedIn.getShoppingCart());
        this.currentlyLoggedIn.getGames().addAll(this.currentlyLoggedIn.getShoppingCart());

        Set<GameTitleDto> names = this.currentlyLoggedIn.getShoppingCart().stream()
                .map(g -> this.modelMapper.map(g, GameTitleDto.class))
                .collect(Collectors.toSet());

        this.currentlyLoggedIn.setShoppingCart(new LinkedHashSet<>());
        this.userRepository.saveAndFlush(currentlyLoggedIn);
        return names;
    }
}
