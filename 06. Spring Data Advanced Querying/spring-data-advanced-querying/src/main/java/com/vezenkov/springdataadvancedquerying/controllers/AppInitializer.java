package com.vezenkov.springdataadvancedquerying.controllers;

import com.vezenkov.springdataadvancedquerying.entities.Ingredient;
import com.vezenkov.springdataadvancedquerying.entities.Label;
import com.vezenkov.springdataadvancedquerying.entities.Size;
import com.vezenkov.springdataadvancedquerying.repositories.IngredientRepository;
import com.vezenkov.springdataadvancedquerying.repositories.LabelRepository;
import com.vezenkov.springdataadvancedquerying.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AppInitializer implements CommandLineRunner {

    private final ShampooRepository shampooRepository;

    private final IngredientRepository ingredientRepository;

    private final LabelRepository labelRepository;

    @Autowired
    public AppInitializer(ShampooRepository shampooRepository, IngredientRepository ingredientRepository, LabelRepository labelRepository) {
        this.shampooRepository = shampooRepository;
        this.ingredientRepository = ingredientRepository;
        this.labelRepository = labelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //01. Find Shampoos by size
//        this.shampooRepository.findShampoosBySizeOrderByIdAsc(Size.MEDIUM)
//                .forEach(s -> System.out.printf("%s %s %.2flv.%n", s.getBrand(), s.getSize(), s.getPrice()));

        //02. Find Shampoos by size or label
//        Label label = this.labelRepository.findLabelById(10L);
//        this.shampooRepository.findShampoosBySizeOrLabelOrderByPriceAsc(Size.MEDIUM, label)
//                .forEach(s -> System.out.printf("%s %s %.2flv.%n", s.getBrand(), s.getSize(), s.getPrice()));

        //03. Find Shampoos by price higher than
//        this.shampooRepository.findShampoosByPriceGreaterThanOrderByPriceDesc(BigDecimal.valueOf(5))
//                .forEach(s -> System.out.printf("%s %s %.2flv.%n", s.getBrand(), s.getSize(), s.getPrice()));

        //04. Find Ingredients by name starting with
//        this.ingredientRepository.findIngredientsByNameStartingWith("m")
//                .forEach(i -> System.out.printf("%s%n", i.getName()));

        //05. Find Ingredients by name
//        this.ingredientRepository.findIngredientsByNameInOrderByPriceAsc(List.of("Lavender", "Herbs", "Apple"))
//                .forEach(i -> System.out.printf("%s%n", i.getName()));

        //06. Count Shampoos by price less than
//        System.out.println(this.shampooRepository.countShampoosByPriceLessThan(BigDecimal.valueOf(8.5)));

        //07. Find Shampoos by Ingredients
//        this.shampooRepository.findShampoosByIngredients(this.ingredientRepository.findIngredientsByNameIn(List.of("Berry", "Mineral-Collagen")))
//                .forEach(s -> System.out.printf("%s%n", s.getBrand()));

        //08. Find Shampoos by Ingredients Count
//        this.shampooRepository.findShampoosByIngredientsCountLessThan(2)
//                .forEach(s -> System.out.printf("%s%n", s.getBrand()));

        //09. Delete Ingredients by Name
//        System.out.println(this.ingredientRepository.deleteByName("Apple"));

        //10. Update Ingredients' Price
//        System.out.println(this.ingredientRepository.updateAllIngredientsBy10Percent());

        //11. Update Ingredients by Names
        System.out.println(this.ingredientRepository.updateIngredientsPriceByName(BigDecimal.valueOf(69), List.of("Apple", "Nettle")));
    }
}
