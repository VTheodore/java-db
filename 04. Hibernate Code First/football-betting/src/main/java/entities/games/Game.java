package entities.games;

import entities.player.Team;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Game {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotNull
    @ManyToOne(targetEntity = Team.class)
    @JoinColumn(name = "home_team", referencedColumnName = "id")
    private Team homeTeam;

    @NonNull
    @NotNull
    @ManyToOne(targetEntity = Team.class)
    @JoinColumn(name = "away_team", referencedColumnName = "id")
    private Team awayTeam;

    @NonNull
    @NotNull
    @PositiveOrZero
    @Column(name = "home_goals")
    private byte homeGoals;

    @NonNull
    @NotNull
    @PositiveOrZero
    @Column(name = "away_goals")
    private byte awayGoals;

    @NonNull
    @NotNull
    @PastOrPresent
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @NonNull
    @NotNull
    @Min(1)
    @Column(name = "home_team_win_bet_rate")
    private BigDecimal homeTeamWinBetRate;

    @NonNull
    @NotNull
    @Min(1)
    @Column(name = "away_team_win_bet_rate")
    private BigDecimal awayTeamWinBetRate;

    @NonNull
    @NotNull
    @Min(1)
    @Column(name = "draw_game_bet_rate")
    private BigDecimal drawGameBetRate;

    @ManyToOne(targetEntity = Round.class)
    @JoinColumn(name = "round_id", referencedColumnName = "id")
    private Round round;

    @ManyToOne(targetEntity = Competition.class)
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    private Competition competition;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id &&
                homeGoals == game.homeGoals &&
                awayGoals == game.awayGoals &&
                dateTime.equals(game.dateTime) &&
                homeTeamWinBetRate.equals(game.homeTeamWinBetRate) &&
                awayTeamWinBetRate.equals(game.awayTeamWinBetRate) &&
                drawGameBetRate.equals(game.drawGameBetRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, homeGoals, awayGoals, dateTime, homeTeamWinBetRate, awayTeamWinBetRate, drawGameBetRate);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", homeGoals=" + homeGoals +
                ", awayGoals=" + awayGoals +
                ", dateTime=" + dateTime +
                ", homeTeamWinBetRate=" + homeTeamWinBetRate +
                ", awayTeamWinBetRate=" + awayTeamWinBetRate +
                ", drawGameBetRate=" + drawGameBetRate +
                '}';
    }
}
