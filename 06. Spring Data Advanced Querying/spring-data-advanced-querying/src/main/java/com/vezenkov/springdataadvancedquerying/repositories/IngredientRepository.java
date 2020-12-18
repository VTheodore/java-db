package com.vezenkov.springdataadvancedquerying.repositories;

import com.vezenkov.springdataadvancedquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findIngredientsByNameStartingWith(String substrName);

    List<Ingredient> findIngredientsByNameInOrderByPriceAsc(Iterable<String> names);

    List<Ingredient> findIngredientsByNameIn(Iterable<String> names);

    @Modifying
    @Transactional
    @Query("DELETE FROM Ingredient i WHERE i.name = :name")
    int deleteByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient i SET i.price = i.price * 1.1")
    int updateAllIngredientsBy10Percent();

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient i SET i.price = :newPrice WHERE i.name IN :names")
    int updateIngredientsPriceByName(@Param("newPrice") BigDecimal newPrice, @Param("names") Iterable<String> names);
}
