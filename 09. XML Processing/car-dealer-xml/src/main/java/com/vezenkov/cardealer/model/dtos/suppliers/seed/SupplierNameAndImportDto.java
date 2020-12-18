package com.vezenkov.cardealer.model.dtos.suppliers.seed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "supplier")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierNameAndImportDto {

    @XmlAttribute(name = "name")
    @NotNull(message = "Suppliers' name cannot be null.")
    @Length(min = 3, max = 100, message = "Suppliers' name must be between 3 and 100 characters.")
    private String name;

    @XmlAttribute(name = "is-importer")
    @NotNull
    private Boolean isImported;
}
