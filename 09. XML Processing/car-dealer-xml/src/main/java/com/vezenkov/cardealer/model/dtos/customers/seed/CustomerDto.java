package com.vezenkov.cardealer.model.dtos.customers.seed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.*;
import java.util.Date;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    @XmlAttribute(name = "name")
    @NotNull(message = "Customers' name cannot be null.")
    @Length(min = 3, max = 60, message = "Length must be between 3 and 60 characters.")
    private String name;

    @XmlElement(name = "birth-date")
    @NotNull(message = "Customers' birth date cannot be null.")
    @Past
    private Date birthDate;

    @XmlElement(name = "is-young-driver")
    @NotNull(message = "Customers' info whether he is a young driver cannot be null.")
    private Boolean isYoungDriver;
}
