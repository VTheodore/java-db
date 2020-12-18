package com.vezenkov.cardealer.model.dtos.supplier;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierNameAndCountDto {
    @Expose
    private Long id;

    @Expose
    private String name;

    @Expose
    private int partsCount;
}
