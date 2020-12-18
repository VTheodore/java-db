package alararestaurant.service;

import alararestaurant.domain.entities.Category;

public interface CategoryService {

    String exportCategoriesByCountOfItems();

    Category getCategoryByName(String name);

    Category save(String name);
}
