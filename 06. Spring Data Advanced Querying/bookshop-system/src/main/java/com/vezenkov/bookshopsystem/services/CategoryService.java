package com.vezenkov.bookshopsystem.services;

import com.vezenkov.bookshopsystem.entitites.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories(String filePath) throws IOException;

    Set<Category> getRandomCategories();
}
