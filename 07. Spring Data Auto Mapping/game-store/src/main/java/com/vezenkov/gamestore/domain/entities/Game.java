package com.vezenkov.gamestore.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Game {
    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(unique = true, nullable = false)
    private String title;

    @Column(name = "video_url")
    private String trailer;

    @Column(name = "image_thumbnail")
    private String thumbnailURL;

    private double size;

    @NonNull
    @Column(nullable = false)
    private BigDecimal price;

    private String description;

    @NonNull
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;
}
