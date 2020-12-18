package com.vezenkov.productshopxml.data.dtos.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCustomDto {
    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "products-count")
    private long productsCount;

    @XmlElement(name = "average-price")
    private BigDecimal averagePrice;

    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;
}
