package alararestaurant.service.impl;

import alararestaurant.domain.entities.Category;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        // TODO : Implement me
        return null;
    }

    @Override
    public Category getCategoryByName(String name) {
        return this.categoryRepository.findCategoryByName(name);
    }

    @Override
    public Category save(String name) {
        Category category = new Category();
        category.setName(name);
        return this.categoryRepository.save(category);
    }
}
