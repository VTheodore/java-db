package com.vezenkov.productshopxml.data.dtos.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductNameAndPriceDto {
    @NotNull(message = "Product name must not be null.")
    @Length(min = 3, message = "Product name must be at least 3 characters.")
    @XmlElement(name = "name")
    private String name;

    @NotNull(message = "Product price must not be null.")
    @Positive(message = "Product price must be positive")
    @XmlElement(name = "price")
    private BigDecimal price;
}
