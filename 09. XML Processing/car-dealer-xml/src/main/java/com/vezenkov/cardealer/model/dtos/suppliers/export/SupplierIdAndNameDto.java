package com.vezenkov.cardealer.model.dtos.suppliers.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "supplier")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierIdAndNameDto {
    @XmlAttribute(name = "id")
    private long id;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "parts-count")
    private int partsCount;
}
