package com.vezenkov.cardealer.model.dtos.cars.export;

import com.vezenkov.cardealer.model.dtos.parts.export.PartNameAndPriceRootDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarPartsDto {
    @XmlAttribute(name = "make")
    private String make;

    @XmlAttribute(name = "name")
    private String model;

    @XmlAttribute(name = "travelled-distance")
    private String travelledDistance;

    @XmlElement(name = "parts")
    private PartNameAndPriceRootDto parts;
}
