package com.vezenkov.productshopxml.data.dtos.users;

import com.vezenkov.productshopxml.data.dtos.products.ProductToSellDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNameDto {
    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElement(name = "sold-products")
    private ProductToSellDto productsToSell;
}
