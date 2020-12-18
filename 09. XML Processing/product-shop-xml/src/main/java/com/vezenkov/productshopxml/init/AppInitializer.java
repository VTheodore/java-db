package com.vezenkov.productshopxml.init;

import com.vezenkov.productshopxml.data.dtos.categories.CategoryByProductRootDto;
import com.vezenkov.productshopxml.data.dtos.categories.CategorySeedRootDto;
import com.vezenkov.productshopxml.data.dtos.products.ProductInRangeDto;
import com.vezenkov.productshopxml.data.dtos.products.ProductSeedRootDto;
import com.vezenkov.productshopxml.data.dtos.users.UserProductsCountRootDto;
import com.vezenkov.productshopxml.data.dtos.users.UserProductsRootDto;
import com.vezenkov.productshopxml.data.dtos.users.UserSeedRootDto;
import com.vezenkov.productshopxml.services.CategoryService;
import com.vezenkov.productshopxml.services.ProductService;
import com.vezenkov.productshopxml.services.UserService;
import com.vezenkov.productshopxml.util.XMLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import static com.vezenkov.productshopxml.util.Constants.*;

@Component
public class AppInitializer implements CommandLineRunner {
    private final CategoryService categoryService;

    private final ProductService productService;

    private final UserService userService;

    private final XMLParser xmlParser;

    @Autowired
    public AppInitializer(CategoryService categoryService, ProductService productService, UserService userService, XMLParser xmlParser) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedCategories();
//        this.seedUsers();
//        this.seedProducts();
//        this.exportProductsInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
//        this.exportSuccessfullySoldProducts();
//        this.exportCategoriesByProducts();
        this.exportUsersWithProductsCount();
    }

    private void exportUsersWithProductsCount() throws JAXBException {
        UserProductsCountRootDto users = this.userService.getUsersWithSoldProductWithCount();
        File file = new File(OUTPUT_FILE_PATH, USERS_AND_PRODUCTS);
        this.xmlParser.exportToXML(users, file);
    }

    private void exportCategoriesByProducts() throws JAXBException {
        CategoryByProductRootDto categoriesByProduct = this.categoryService.getCategoriesByProduct();
        File file = this.createFile(OUTPUT_FILE_PATH, CATEGORIES_BY_PRODUCTS);
        this.xmlParser.exportToXML(categoriesByProduct, file);
    }

    private void exportSuccessfullySoldProducts() throws JAXBException {
        UserProductsRootDto userProductsRootDto = this.userService.getUsersBySuccessfullySoldProduct();
        File file = this.createFile(OUTPUT_FILE_PATH, USER_SOLD_PRODUCTS);
        this.xmlParser.exportToXML(userProductsRootDto, file);

    }

    private void exportProductsInRange(BigDecimal lowerBound, BigDecimal upperBound) throws JAXBException {
        ProductInRangeDto productsInRange = this.productService.getProductsInRange(lowerBound, upperBound);
        File file = this.createFile(OUTPUT_FILE_PATH, PRODUCT_IN_RANGE);
        this.xmlParser.exportToXML(productsInRange, file);
    }

    private void seedProducts() {
        ProductSeedRootDto productSeedRootDto = this.xmlParser.importFromXML(ProductSeedRootDto.class, PRODUCTS_FILE_PATH);
        this.productService.seedProducts(productSeedRootDto.getProducts());
    }

    private void seedUsers() {
        UserSeedRootDto userSeedRootDto = this.xmlParser.importFromXML(UserSeedRootDto.class, USERS_FILE_PATH);
        this.userService.seedUsers(userSeedRootDto.getUsers());
    }

    private void seedCategories() {
        CategorySeedRootDto categorySeedRootDto = this.xmlParser.importFromXML(CategorySeedRootDto.class, CATEGORIES_FILE_PATH);
        this.categoryService.seedCategories(categorySeedRootDto.getCategories());
    }

    private File createFile(String folderDirectory, String fileName) {
        try {
            File file = new File(folderDirectory + fileName);

            if (file.createNewFile()) {
                System.out.printf("Created file %s in %s%n", fileName, folderDirectory);
            }

            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
