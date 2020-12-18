package com.vezenkov.springdataadvancedquerying.repositories;

import com.vezenkov.springdataadvancedquerying.entities.Ingredient;
import com.vezenkov.springdataadvancedquerying.entities.Label;
import com.vezenkov.springdataadvancedquerying.entities.Shampoo;
import com.vezenkov.springdataadvancedquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findShampoosBySizeOrderByIdAsc(Size size);

    List<Shampoo> findShampoosBySizeOrLabelOrderByPriceAsc(Size size, Label label);

    List<Shampoo> findShampoosByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    long countShampoosByPriceLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i IN :ingredients")
    List<Shampoo> findShampoosByIngredients(@Param("ingredients") Iterable<Ingredient> ingredients);

    @Query("SELECT s FROM Shampoo s WHERE s.ingredients.size < :count")
    List<Shampoo> findShampoosByIngredientsCountLessThan(@Param("count") int count);
}
