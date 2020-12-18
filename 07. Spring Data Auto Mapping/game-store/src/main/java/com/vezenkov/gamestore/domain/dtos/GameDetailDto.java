package com.vezenkov.gamestore.domain.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GameDetailDto {
    private String title;

    private BigDecimal price;

    private String description;

    private LocalDate releaseDate;
}
