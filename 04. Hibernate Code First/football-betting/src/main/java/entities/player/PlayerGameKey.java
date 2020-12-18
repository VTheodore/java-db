package entities.player;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class PlayerGameKey implements Serializable {
    @Column(name = "player_id")
    private long playerId;

    @Column(name = "game_id")
    private long gameId;
}
