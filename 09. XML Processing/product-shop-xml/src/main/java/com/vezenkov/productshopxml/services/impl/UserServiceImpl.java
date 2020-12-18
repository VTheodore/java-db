package com.vezenkov.productshopxml.services.impl;

import com.vezenkov.productshopxml.data.dtos.products.ProductBuyerDto;
import com.vezenkov.productshopxml.data.dtos.products.ProductNameAndPriceAttributeDto;
import com.vezenkov.productshopxml.data.dtos.products.ProductToSellDto;
import com.vezenkov.productshopxml.data.dtos.products.ProductToSellWithCountDto;
import com.vezenkov.productshopxml.data.dtos.users.*;
import com.vezenkov.productshopxml.data.entitites.User;
import com.vezenkov.productshopxml.repositories.UserRepository;
import com.vezenkov.productshopxml.services.UserService;
import com.vezenkov.productshopxml.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedUsers(List<UserNameAndAgeDto> userNameAndAgeDtos) {
        userNameAndAgeDtos
                .forEach(userNameAndAgeDto -> {
                    if (this.validationUtil.isValid(userNameAndAgeDto)){
                        User user = this.modelMapper.map(userNameAndAgeDto, User.class);
                        this.userRepository.save(user);
                    } else {
                        this.validationUtil
                                .getViolations(userNameAndAgeDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public User getRandomUser() {
        Random random = new Random();

        long userId = random.nextInt((int) this.userRepository.count()) + 1;
        return this.userRepository.findUserById(userId);
    }

    @Override
    public UserProductsRootDto getUsersBySuccessfullySoldProduct() {
        List<UserNameDto> userNameDtos = new ArrayList<>();

        this.userRepository.findUsersBySuccessfullySoldProduct()
                .forEach(user -> {
                    UserNameDto userNameDto = this.modelMapper.map(user, UserNameDto.class);
                    userNameDtos.add(userNameDto);
                });
        UserProductsRootDto userProductsRootDto = new UserProductsRootDto();
        userProductsRootDto.setUsers(userNameDtos);

        return userProductsRootDto;
    }

    @Override
    public UserProductsCountRootDto getUsersWithSoldProductWithCount() {
        List<UserProductDto> userProductDtos = new ArrayList<>();

        this.userRepository.findUsersBySuccessfullySoldProduct()
                .forEach(user -> {
                    UserProductDto userProductDto = this.modelMapper.map(user, UserProductDto.class);
                    userProductDto.getProductsToSell().setCount(userProductDto.getProductsToSell().getProducts().size());
                    userProductDtos.add(userProductDto);
                });

        UserProductsCountRootDto userProductsCountRootDto = new UserProductsCountRootDto();
        userProductsCountRootDto.setCount(userProductDtos.size());
        userProductsCountRootDto.setUsers(userProductDtos);

        return userProductsCountRootDto;
    }
}
