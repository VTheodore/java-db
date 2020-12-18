package com.vezenkov.productshopxml.data.dtos.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductToSellWithCountDto {
    @XmlAttribute(name = "count")
    private long count;

    @XmlElement(name = "product")
    private List<ProductNameAndPriceAttributeDto> products;
}
