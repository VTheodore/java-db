package com.vezenkov.productshopxml.repositories;

import com.vezenkov.productshopxml.data.entitites.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryById(Long id);

    Category findCategoryByName(String name);

    @Query(value = "SELECT c.name, COUNT(pc.product_id) AS p_count, AVG(p.price), SUM(p.price) FROM products_categories AS pc\n" +
            "JOIN categories c ON pc.category_id = c.category_id\n" +
            "JOIN products p ON pc.product_id = p.product_id\n" +
            "GROUP BY pc.category_id\n" +
            "ORDER BY p_count DESC", nativeQuery = true)
    List<Object[]> findAllCategoriesByProductsCount();
}
