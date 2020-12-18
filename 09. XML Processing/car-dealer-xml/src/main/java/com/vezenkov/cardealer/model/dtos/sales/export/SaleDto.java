package com.vezenkov.cardealer.model.dtos.sales.export;

import com.vezenkov.cardealer.model.dtos.cars.export.CarMakeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "sale")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {
    @XmlElement(name = "car")
    private CarMakeDto car;

    @XmlElement(name = "customer-name")
    private String customerName;

    @XmlElement(name = "discount")
    private BigDecimal discount;

    @XmlElement(name = "price")
    private BigDecimal price;

    @XmlElement(name = "price-with-discount")
    private BigDecimal priceWithDiscount;
}
