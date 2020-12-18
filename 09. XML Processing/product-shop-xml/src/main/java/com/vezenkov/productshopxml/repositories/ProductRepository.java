package com.vezenkov.productshopxml.repositories;

import com.vezenkov.productshopxml.data.entitites.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByPriceBetween(BigDecimal loweBound, BigDecimal upperBound);
}
