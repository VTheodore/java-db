package com.vezenkov.cardealer.model.dtos.parts.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartNameAndPriceRootDto {
    @XmlElement(name = "part")
    private List<PartNameAndPriceDto> parts;
}
