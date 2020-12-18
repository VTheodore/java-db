package entities.bets;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class BetGameKey implements Serializable {
    @Column(name = "game_id")
    private long gameId;

    @Column(name = "bet_id")
    private long betId;
}
