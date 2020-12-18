package com.vezenkov.productsshop.repositories;

import com.vezenkov.productsshop.models.entitites.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByPriceBetweenAndBuyerIsNull(BigDecimal lowerBound, BigDecimal upperBound);
}
