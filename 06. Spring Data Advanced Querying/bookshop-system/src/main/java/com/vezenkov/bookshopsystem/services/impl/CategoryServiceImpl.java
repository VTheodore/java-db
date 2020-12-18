package com.vezenkov.bookshopsystem.services.impl;

import com.vezenkov.bookshopsystem.entitites.Category;
import com.vezenkov.bookshopsystem.repositories.CategoryRepository;
import com.vezenkov.bookshopsystem.services.CategoryService;
import com.vezenkov.bookshopsystem.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final FileUtil fileUtil;

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(FileUtil fileUtil, CategoryRepository categoryRepository) {
        this.fileUtil = fileUtil;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories(String filePath) throws IOException {
        String[] args = this.fileUtil.readFileContent(filePath);

        for (String arg : args) {
            if (!this.categoryRepository.existsCategoryByName(arg)) {
                Category category = new Category(arg);

                this.categoryRepository.saveAndFlush(category);
            }
        }
    }

    @Override
    public Set<Category> getRandomCategories() {
        Random random = new Random();

        int count = random.nextInt(3) + 1;
        int categoriesCount = (int) this.categoryRepository.count();

        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < count; i++) {
            Category category = this.categoryRepository
                    .getOne((long) random.nextInt(categoriesCount) + 1);

            categories.add(category);
        }

        return categories;
    }
}
