package com.vezenkov.bookshopsystem.services;

import com.vezenkov.bookshopsystem.entitites.Category;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface CategoryService {
    void seedCategories(String filePath) throws IOException;

    long getCount();

    Category getCategoryById(Long id);
}
