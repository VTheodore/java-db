package com.vezenkov.gamestore.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRegisterDto {

    @Size(min = 3, max = 100, message = "Length must be between 3 and 100 characters!")
    @Pattern(regexp = "[A-Z].+", message = "Title does not start with an uppercase character!")
    private String title;

    @Positive(message = "Price must be a positive number!")
    private BigDecimal price;

    @Positive(message = "Size must be a positive number!")
    private double size;

    @Size(min = 11, max = 11, message = "The provided url should be exactly 11 characters!")
    private String trailer;

    @Pattern(regexp = "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)", message = "Invalid thumbnail url!")
    private String thumbnailURL;

    @Size(min = 20, message = "Description must be at least 20 characters!")
    private String description;

    private LocalDate releaseDate;
}
