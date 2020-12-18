package com.vezenkov.cardealer.model.dtos.parts.seed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "part")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartDto {

    @XmlAttribute(name = "name")
    @NotNull(message = "Part name cannot be null.")
    @Length(min = 3, max = 100, message = "Part name must be between 3 and 100 characters.")
    private String name;

    @XmlAttribute(name = "price")
    @NotNull(message = "Part price cannot be null.")
    @Positive(message = "Part price cannot be negative or zero.")
    private BigDecimal price;

    @XmlAttribute(name = "quantity")
    @PositiveOrZero(message = "Part quantity cannot be negative.")
    private int quantity;
}
