package com.vezenkov.cardealer.model.dtos.customers.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersOrderedDto {
    @XmlElement(name = "id")
    private long id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "birth-date")
    private Date birthDate;

    @XmlElement(name = "is-young-driver")
    private Boolean isYoungDriver;
}
