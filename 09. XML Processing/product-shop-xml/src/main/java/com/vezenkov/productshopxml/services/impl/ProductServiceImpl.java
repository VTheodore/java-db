package com.vezenkov.productshopxml.services.impl;

import com.vezenkov.productshopxml.data.dtos.products.ProductDto;
import com.vezenkov.productshopxml.data.dtos.products.ProductInRangeDto;
import com.vezenkov.productshopxml.data.dtos.products.ProductNameAndPriceDto;
import com.vezenkov.productshopxml.data.entitites.Product;
import com.vezenkov.productshopxml.repositories.ProductRepository;
import com.vezenkov.productshopxml.services.CategoryService;
import com.vezenkov.productshopxml.services.ProductService;
import com.vezenkov.productshopxml.services.UserService;
import com.vezenkov.productshopxml.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, UserService userService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedProducts(List<ProductNameAndPriceDto> productNameAndPriceDtos) {
        productNameAndPriceDtos
                .forEach(productNameAndPriceDto -> {
                    if (this.validationUtil.isValid(productNameAndPriceDto)) {
                        Product product = this.modelMapper.map(productNameAndPriceDto, Product.class);
                        product.setSeller(this.userService.getRandomUser());

                        Random random = new Random();
                        int chance = random.nextInt(2);

                        if (chance == 1) {
                            product.setBuyer(this.userService.getRandomUser());
                        }

                        product.setCategories(this.categoryService.getRandomCategories());
                        this.productRepository.save(product);
                    } else {
                        this.validationUtil
                                .getViolations(productNameAndPriceDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public ProductInRangeDto getProductsInRange(BigDecimal lowerBound, BigDecimal upperBound) {
        ProductInRangeDto productInRangeDto = new ProductInRangeDto();
        List<ProductDto> productDtos = new ArrayList<>();

        this.productRepository.findProductsByPriceBetween(lowerBound, upperBound)
                .forEach(product -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setName(product.getName());
                    productDto.setSellerName(String.format("%s %s", product.getSeller().getFirstName(), product.getSeller().getLastName()));
                    productDto.setPrice(product.getPrice());
                    productDtos.add(productDto);
                });
        productInRangeDto.setProducts(productDtos);

        return productInRangeDto;
    }
}
