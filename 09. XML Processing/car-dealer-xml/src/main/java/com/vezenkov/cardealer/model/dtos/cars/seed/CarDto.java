package com.vezenkov.cardealer.model.dtos.cars.seed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    @XmlElement(name = "make")
    @NotNull(message = "Car make cannot be null.")
    @Length(min = 3, max = 30, message = "Car make must be between 3 and 30 characters")
    private String make;

    @XmlElement(name = "model")
    @NotNull(message = "Car model cannot be null.")
    @Length(min = 1, max = 60, message = "Car model must be between 3 and 60 characters.")
    private String model;

    @XmlElement(name = "travelled-distance")
    private String travelledDistance;
}
