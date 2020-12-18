package com.vezenkov.bookshopsystem.services.impl;

import com.vezenkov.bookshopsystem.entitites.Category;
import com.vezenkov.bookshopsystem.repositories.CategoryRepository;
import com.vezenkov.bookshopsystem.services.CategoryService;
import com.vezenkov.bookshopsystem.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
    public long getCount() {
        return this.categoryRepository.count();
    }

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.getOne(id);
    }
}
