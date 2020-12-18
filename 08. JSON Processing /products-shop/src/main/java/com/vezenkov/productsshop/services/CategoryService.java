package com.vezenkov.productsshop.services;

import com.vezenkov.productsshop.models.dtos.categories.CategoryByProductDto;
import com.vezenkov.productsshop.models.dtos.categories.CategorySeedDto;
import com.vezenkov.productsshop.models.entitites.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(CategorySeedDto[] categorySeedDtos);

    Set<Category> getRandomCategories();

    List<CategoryByProductDto> getAllCategoriesByProductsCount();
}
