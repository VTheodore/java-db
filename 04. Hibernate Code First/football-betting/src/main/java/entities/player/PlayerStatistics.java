package entities.player;

import entities.games.Game;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Entity
@Table(name = "player_statistics")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PlayerStatistics {

    @EmbeddedId
    private PlayerGameKey id;

    @ManyToOne
    @MapsId("player_id")
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @MapsId("game_id")
    @JoinColumn(name = "game_id")
    private Game game;

    @NonNull
    @NotNull
    @PositiveOrZero
    @Column(name = "scored_goals")
    private byte scoredGoals;

    @NonNull
    @NotNull
    @PositiveOrZero
    @Column(name = "player_assists")
    private byte playerAssists;

    @NonNull
    @NotNull
    @PositiveOrZero
    @Max(150)
    @Column(name = "played_minutes")
    private double playedMinutes;
}
