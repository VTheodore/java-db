package com.vezenkov.productsshop.services;

import com.vezenkov.productsshop.models.dtos.users.UserSeedDto;
import com.vezenkov.productsshop.models.dtos.users.UserSoldDto;
import com.vezenkov.productsshop.models.dtos.users.UserWithCountDto;
import com.vezenkov.productsshop.models.entitites.User;

import java.util.List;

public interface UserService {
    void seedUsers(UserSeedDto[] userSeedDtos);

    User getRandomUser();

    List<UserSoldDto> getUsersWithSoldProductsOrderedByNames();

    UserWithCountDto getUsersWithAtLeastOneSoldItemOrderedByCountDescAndName();
}
