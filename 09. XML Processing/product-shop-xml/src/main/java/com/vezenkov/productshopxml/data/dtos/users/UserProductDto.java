package com.vezenkov.productshopxml.data.dtos.users;

import com.vezenkov.productshopxml.data.dtos.products.ProductToSellWithCountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProductDto {
    @XmlElement(name = "sold-products")
    private ProductToSellWithCountDto productsToSell;
}
