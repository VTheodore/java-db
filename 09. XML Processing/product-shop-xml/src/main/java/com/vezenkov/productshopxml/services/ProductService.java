package com.vezenkov.productshopxml.services;

import com.vezenkov.productshopxml.data.dtos.products.ProductInRangeDto;
import com.vezenkov.productshopxml.data.dtos.products.ProductNameAndPriceDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts(List<ProductNameAndPriceDto> productNameAndPriceDtos);

    ProductInRangeDto getProductsInRange(BigDecimal lowerBound, BigDecimal upperBound);
}
