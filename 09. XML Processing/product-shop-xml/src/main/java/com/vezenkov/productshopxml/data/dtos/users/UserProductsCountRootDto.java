package com.vezenkov.productshopxml.data.dtos.users;

import com.vezenkov.productshopxml.data.dtos.products.ProductToSellWithCountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProductsCountRootDto {
    @XmlAttribute(name = "count")
    private long count;

    @XmlElement(name = "user")
    private List<UserProductDto> users;
}
