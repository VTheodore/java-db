package com.vezenkov.productsshop.services.impl;

import com.vezenkov.productsshop.models.dtos.categories.CategoryByProductDto;
import com.vezenkov.productsshop.models.dtos.categories.CategorySeedDto;
import com.vezenkov.productsshop.models.entitites.Category;
import com.vezenkov.productsshop.repositories.CategoryRepository;
import com.vezenkov.productsshop.services.CategoryService;
import com.vezenkov.productsshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ValidationUtil validationUtil;

    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        Arrays.stream(categorySeedDtos)
                .forEach(categorySeedDto -> {
                    if (this.validationUtil.isValid(categorySeedDto)) {
                        if (this.categoryRepository.findCategoryByName(categorySeedDto.getName()) == null) {
                            Category category = this.modelMapper.map(categorySeedDto, Category.class);
                            this.categoryRepository.saveAndFlush(category);
                        }
                    } else {
                        this.validationUtil.getViolations(categorySeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Set<Category> getRandomCategories() {
        Random random = new Random();

        int categoriesCount = random.nextInt(4) + 1; // between 1 and 4 categories
        Set<Category> randomCategories = new HashSet<>();

        for (int i = 0; i < categoriesCount; i++) {
            randomCategories.add(this.getRandomCategory(random));
        }

        return randomCategories;
    }

    @Override
    public List<CategoryByProductDto> getAllCategoriesByProductsCount() {
        return this.categoryRepository.findAllCategoriesByProductsCount()
                .stream()
                .map(o -> {
                    CategoryByProductDto categoryByProductDto = new CategoryByProductDto();
                    categoryByProductDto.setName(o[0].toString());
                    categoryByProductDto.setProductsCount(Long.parseLong(o[1].toString()));
                    categoryByProductDto.setAveragePrice(new BigDecimal(o[2].toString()));
                    categoryByProductDto.setTotalRevenue(new BigDecimal(o[3].toString()));
                    return categoryByProductDto;
                }).collect(Collectors.toList());
    }

    private Category getRandomCategory(Random random) {
        long categoryId = random.nextInt((int)this.categoryRepository.count()) + 1;
        return this.categoryRepository.findCategoryById(categoryId);
    }
}
