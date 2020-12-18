package com.vezenkov.productsshop.controllers;

import com.google.gson.Gson;
import com.vezenkov.productsshop.models.dtos.categories.CategoryByProductDto;
import com.vezenkov.productsshop.models.dtos.categories.CategorySeedDto;
import com.vezenkov.productsshop.models.dtos.products.ProductInRangeDto;
import com.vezenkov.productsshop.models.dtos.products.ProductSeedDto;
import com.vezenkov.productsshop.models.dtos.users.UserSeedDto;
import com.vezenkov.productsshop.models.dtos.users.UserSoldDto;
import com.vezenkov.productsshop.models.dtos.users.UserWithCountDto;
import com.vezenkov.productsshop.services.CategoryService;
import com.vezenkov.productsshop.services.ProductService;
import com.vezenkov.productsshop.services.UserService;
import com.vezenkov.productsshop.util.FileIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static com.vezenkov.productsshop.util.Constants.*;

@Component
public class AppController implements CommandLineRunner {
    private final Gson gson;

    private final CategoryService categoryService;

    private final UserService userService;

    private final ProductService productService;

    private final FileIOUtil fileIOUtil;

    @Autowired
    public AppController(Gson gson, CategoryService categoryService, UserService userService, ProductService productService, FileIOUtil fileIOUtil) {
        this.gson = gson;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.fileIOUtil = fileIOUtil;
    }

    @Override
    public void run(String... args) throws Exception {
        //JSON
        {
//        this.seedCategoriesFrom();
//        this.seedUsersFrom();
//        this.seedProductsFrom();

//        this.writeInFileProductsInRange();
//        this.writeInFileUserSoldProducts();
//        this.writeInFileCategoriesByProductsCount();
//        this.writeInFileUsersAndProducts();
        }
    }

    private void writeInFileUsersAndProducts() {
        UserWithCountDto userWithCountDto = this.userService.getUsersWithAtLeastOneSoldItemOrderedByCountDescAndName();
        this.fileIOUtil.writeToFile(OUTPUT_FILE_FOLDER, USERS_AND_PRODUCTS, this.gson.toJson(userWithCountDto));
    }

    private void writeInFileCategoriesByProductsCount() {
        List<CategoryByProductDto> allCategories = this.categoryService.getAllCategoriesByProductsCount();
        this.fileIOUtil.writeToFile(OUTPUT_FILE_FOLDER, CATEGORIES_BY_COUNT_OF_PRODUCTS, this.gson.toJson(allCategories));
    }

    private void writeInFileUserSoldProducts() {
        List<UserSoldDto> userSoldDtos = this.userService.getUsersWithSoldProductsOrderedByNames();
        this.fileIOUtil.writeToFile(OUTPUT_FILE_FOLDER, USERS_SOLD_PRODUCTS, this.gson.toJson(userSoldDtos));
    }

    private void writeInFileProductsInRange() {
        List<ProductInRangeDto> productsInRange = this.productService.getProductsInRange();
        this.fileIOUtil.writeToFile(OUTPUT_FILE_FOLDER, PRODUCTS_IN_RANGE, this.gson.toJson(productsInRange));
    }

    private void seedProductsFrom() throws FileNotFoundException {
        ProductSeedDto[] productSeedDtos = this.gson.fromJson(new FileReader(PRODUCTS_FILE_PATH), ProductSeedDto[].class);
        this.productService.seedProducts(productSeedDtos);
    }

    private void seedUsersFrom() throws FileNotFoundException {
        UserSeedDto[] userSeedDtos = this.gson.fromJson(new FileReader(USERS_FILE_PATH), UserSeedDto[].class);
        this.userService.seedUsers(userSeedDtos);
    }

    private void seedCategoriesFrom() throws FileNotFoundException {
        CategorySeedDto[] categorySeedDtos = gson.fromJson(new FileReader(CATEGORIES_FILE_PATH), CategorySeedDto[].class);
        this.categoryService.seedCategories(categorySeedDtos);
    }
}
