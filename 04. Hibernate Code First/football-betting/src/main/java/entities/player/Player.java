package entities.player;

import entities.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Entity
@Table(name = "players")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Player extends BaseEntity {
    @NonNull
    @NotNull
    @Positive
    @Column(name = "squad_number")
    private byte squadNumber;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;

    @NonNull
    @NotNull
    @Column(name = "is_injured")
    private boolean isInjured;

    @OneToMany(mappedBy = "player", targetEntity = PlayerStatistics.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PlayerStatistics> playerStatistics;
}
