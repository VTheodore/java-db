package com.vezenkov.productshopxml.data.dtos.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryNameDto {
    @NotNull(message = "Category name must not be null.")
    @Length(min = 3, max = 15, message = "Category name must be between 3 and 15 characters.")
    @XmlElement(name = "name")
    private String name;
}
