package entities.games;

import entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "competitions")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Competition extends BaseEntity {
    @NonNull
    @NotNull
    @ManyToOne(targetEntity = CompetitionType.class)
    @JoinColumn(name = "competiion_type", referencedColumnName = "id")
    private CompetitionType competitionType;

    @OneToMany(mappedBy = "competition", targetEntity = Game.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Game> games;
}
