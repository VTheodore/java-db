package com.vezenkov.productsshop.services.impl;

import com.vezenkov.productsshop.models.dtos.products.ProductInRangeDto;
import com.vezenkov.productsshop.models.dtos.products.ProductSeedDto;
import com.vezenkov.productsshop.models.entitites.Product;
import com.vezenkov.productsshop.models.entitites.User;
import com.vezenkov.productsshop.repositories.ProductRepository;
import com.vezenkov.productsshop.services.CategoryService;
import com.vezenkov.productsshop.services.ProductService;
import com.vezenkov.productsshop.services.UserService;
import com.vezenkov.productsshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final UserService userService;

    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedProducts(ProductSeedDto[] productSeedDtos) {
        Arrays.stream(productSeedDtos)
                .forEach(productSeedDto -> {
                    if (this.validationUtil.isValid(productSeedDto)){
                        Product product = this.modelMapper.map(productSeedDto, Product.class);
                        this.seedBuyerAndSeller(product);
                        product.setCategories(this.categoryService.getRandomCategories());
                        this.productRepository.saveAndFlush(product);
                    } else {
                        this.validationUtil.getViolations(productSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public List<ProductInRangeDto> getProductsInRange() {
        return this.productRepository
                .findProductsByPriceBetweenAndBuyerIsNull(BigDecimal.valueOf(500), BigDecimal.valueOf(1000))
                .stream()
                .map(product -> {
                    ProductInRangeDto productInRangeDto = this.modelMapper.map(product, ProductInRangeDto.class);
                    productInRangeDto.setSeller(String.format("%s %s", product.getSeller().getFirstName(), product.getSeller().getLastName()));

                    return productInRangeDto;
                })
                 .collect(Collectors.toList());
    }

    private void seedBuyerAndSeller(Product product) {
        User seller = this.userService.getRandomUser();
        product.setSeller(seller);

        Random random = new Random();
        int num = random.nextInt(3); // 0, 1, 2

        if (num != 0) { // 33.33% chance to not have been bought
            User buyer = this.userService.getRandomUser();
            product.setBuyer(buyer);
        }
    }
}
