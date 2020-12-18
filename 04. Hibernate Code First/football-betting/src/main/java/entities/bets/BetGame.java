package entities.bets;

import entities.games.Game;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bet_games")
@Data
@NoArgsConstructor
public class BetGame {
    @EmbeddedId
    private BetGameKey id;

    @ManyToOne
    @MapsId("game_id")
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @MapsId("bet_id")
    @JoinColumn(name = "bet_id")
    private Bet bet;

    @ManyToOne(targetEntity = ResultPrediction.class)
    @JoinColumn(name = "result_prediction", referencedColumnName = "id")
    private ResultPrediction resultPrediction;


}
