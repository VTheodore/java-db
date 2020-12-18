package com.vezenkov.productshopxml.services;

import com.vezenkov.productshopxml.data.dtos.categories.CategoryByProductRootDto;
import com.vezenkov.productshopxml.data.dtos.categories.CategoryNameDto;
import com.vezenkov.productshopxml.data.entitites.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(List<CategoryNameDto> categoryNameDtos);

    Set<Category> getRandomCategories();

    CategoryByProductRootDto getCategoriesByProduct();
}
