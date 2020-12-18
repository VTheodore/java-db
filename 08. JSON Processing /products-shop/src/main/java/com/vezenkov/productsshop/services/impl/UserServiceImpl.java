package com.vezenkov.productsshop.services.impl;

import com.vezenkov.productsshop.models.dtos.products.ProductNameAndPriceDto;
import com.vezenkov.productsshop.models.dtos.products.ProductSoldDto;
import com.vezenkov.productsshop.models.dtos.products.ProductSoldWithCountDto;
import com.vezenkov.productsshop.models.dtos.users.UserProductsDto;
import com.vezenkov.productsshop.models.dtos.users.UserSeedDto;
import com.vezenkov.productsshop.models.dtos.users.UserSoldDto;
import com.vezenkov.productsshop.models.dtos.users.UserWithCountDto;
import com.vezenkov.productsshop.models.entitites.Product;
import com.vezenkov.productsshop.models.entitites.User;
import com.vezenkov.productsshop.repositories.UserRepository;
import com.vezenkov.productsshop.services.UserService;
import com.vezenkov.productsshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ValidationUtil validationUtil;

    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) {
        Arrays.stream(userSeedDtos)
                .forEach(userSeedDto -> {
                    if (this.validationUtil.isValid(userSeedDto)) {
                        User user = this.modelMapper.map(userSeedDto, User.class);

                        this.userRepository.saveAndFlush(user);
                    } else {
                        this.validationUtil.getViolations(userSeedDto)
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
    public List<UserSoldDto> getUsersWithSoldProductsOrderedByNames() {
        return this.userRepository.findAllUsersWithAtLeastOneItemSold()
                .stream()
                .map(user -> {
                    UserSoldDto userSoldDto = this.modelMapper.map(user, UserSoldDto.class);
                    userSoldDto.setSoldProducts(user
                            .getProductsToSell()
                            .stream()
                            .filter(p -> p.getBuyer() != null)
                            .map(p -> this.modelMapper.map(p, ProductSoldDto.class))
                            .collect(Collectors.toList()));

                    return userSoldDto;
                }).collect(Collectors.toList());
    }

    @Override
    public UserWithCountDto getUsersWithAtLeastOneSoldItemOrderedByCountDescAndName() {
        List<User> users = this.userRepository.findAllUsersWithAtLeastOneItemSoldOrderedByCountAndLastNameDesc();
        UserWithCountDto userWithCountDto = new UserWithCountDto();
        userWithCountDto.setCount(users.size());

        List<UserProductsDto> userProductsDtos = new ArrayList<>();
        users
                .forEach(user -> {
                    UserProductsDto userProductsDto = this.modelMapper.map(user, UserProductsDto.class);
                    ProductSoldWithCountDto productSoldWithCountDto = new ProductSoldWithCountDto();

                    List<Product> soldProducts = user
                            .getProductsToSell()
                            .stream()
                            .filter(p -> p.getBuyer() != null)
                            .collect(Collectors.toList());

                    productSoldWithCountDto.setCount(soldProducts.size());

                    List<ProductNameAndPriceDto> productNameAndPriceDtos = soldProducts
                            .stream()
                            .map(p -> this.modelMapper.map(p, ProductNameAndPriceDto.class))
                            .collect(Collectors.toList());

                    productSoldWithCountDto.setProducts(productNameAndPriceDtos);
                    userProductsDto.setSoldProducts(productSoldWithCountDto);

                    userProductsDtos.add(userProductsDto);
                });

        userWithCountDto.setUsers(userProductsDtos);
        return userWithCountDto;
    }

}
