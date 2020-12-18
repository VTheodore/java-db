package com.vezenkov.gamestore.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameTitleAndPriceDto {
    private String title;

    private BigDecimal price;
}
