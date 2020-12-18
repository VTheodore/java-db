package com.vezenkov.productshopxml.services.impl;

import com.vezenkov.productshopxml.data.dtos.categories.CategoryByProductRootDto;
import com.vezenkov.productshopxml.data.dtos.categories.CategoryCustomDto;
import com.vezenkov.productshopxml.data.dtos.categories.CategoryNameDto;
import com.vezenkov.productshopxml.data.entitites.Category;
import com.vezenkov.productshopxml.repositories.CategoryRepository;
import com.vezenkov.productshopxml.services.CategoryService;
import com.vezenkov.productshopxml.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories(List<CategoryNameDto> categoryNameDtos) {
        categoryNameDtos
                .forEach(categoryNameDto -> {
                    if (this.categoryRepository.findCategoryByName(categoryNameDto.getName()) == null) {
                        if (this.validationUtil.isValid(categoryNameDto)) {
                            Category category = this.modelMapper.map(categoryNameDto, Category.class);
                            this.categoryRepository.save(category);
                        } else {
                            this.validationUtil
                                    .getViolations(categoryNameDto)
                                    .stream()
                                    .map(ConstraintViolation::getMessage)
                                    .forEach(System.out::println);
                        }
                    }
                });
    }

    @Override
    public Set<Category> getRandomCategories() {
        Random random = new Random();
        int count = random.nextInt(3) + 1;

        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < count; i++) {
            categories.add(this.getRandomCategory(random));
        }

        return categories;
    }

    @Override
    public CategoryByProductRootDto getCategoriesByProduct() {
        List<CategoryCustomDto> categoryCustomDtos = new ArrayList<>();

        this.categoryRepository.findAllCategoriesByProductsCount()
                .forEach(objects -> {
                    CategoryCustomDto categoryCustomDto = new CategoryCustomDto();
                    categoryCustomDto.setName(objects[0].toString());
                    categoryCustomDto.setProductsCount(Long.parseLong(objects[1].toString()));
                    categoryCustomDto.setAveragePrice(new BigDecimal(objects[2].toString()));
                    categoryCustomDto.setTotalRevenue(new BigDecimal((objects[3].toString())));

                    categoryCustomDtos.add(categoryCustomDto);
                });

        CategoryByProductRootDto categoryByProductRootDto = new CategoryByProductRootDto();
        categoryByProductRootDto.setCategories(categoryCustomDtos);

        return categoryByProductRootDto;
    }

    private Category getRandomCategory(Random random) {
        long categoryId = random.nextInt((int) this.categoryRepository.count());
        return this.categoryRepository.findCategoryById(categoryId);
    }
}
