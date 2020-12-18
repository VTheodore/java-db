package entities.games;

import entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rounds")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Round extends BaseEntity {
    @OneToMany(mappedBy = "round", targetEntity = Game.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Game> games;
}
