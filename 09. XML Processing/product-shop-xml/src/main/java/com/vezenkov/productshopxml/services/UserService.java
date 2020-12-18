package com.vezenkov.productshopxml.services;

import com.vezenkov.productshopxml.data.dtos.users.UserNameAndAgeDto;
import com.vezenkov.productshopxml.data.dtos.users.UserProductsCountRootDto;
import com.vezenkov.productshopxml.data.dtos.users.UserProductsRootDto;
import com.vezenkov.productshopxml.data.entitites.User;

import java.util.List;

public interface UserService {
    void seedUsers(List<UserNameAndAgeDto> userNameAndAgeDtos);

    User getRandomUser();

    UserProductsRootDto getUsersBySuccessfullySoldProduct();

    UserProductsCountRootDto getUsersWithSoldProductWithCount();
}
