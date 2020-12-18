package com.vezenkov.productsshop.services;

import com.vezenkov.productsshop.models.dtos.products.ProductInRangeDto;
import com.vezenkov.productsshop.models.dtos.products.ProductSeedDto;

import java.util.List;

public interface ProductService {
    void seedProducts(ProductSeedDto[] productSeedDtos);

    List<ProductInRangeDto> getProductsInRange();
}
