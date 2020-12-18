package com.vezenkov.productshopxml.data.dtos.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBuyerDto {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "price")
    private BigDecimal price;

    @XmlElement(name = "buyer-first-name")
    private String buyerFirstName;

    @XmlElement(name = "buyer-last-name")
    private String buyerLastName;
}
