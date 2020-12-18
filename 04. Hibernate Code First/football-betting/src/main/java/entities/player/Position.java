package entities.player;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "positions")
@Data
@NoArgsConstructor
public class Position {
    @Id
    @Size(min = 2, max = 2)
    @Column(name = "id", length = 2)
    private String id;

    @Size(max = 100)
    @Column(name = "position_description", length = 100)
    private String positionDescription;

    @OneToMany(targetEntity = Player.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Player> players;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return id.equals(position.id) &&
                positionDescription.equals(position.positionDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, positionDescription);
    }

    @Override
    public String toString() {
        return "Position{" +
                "id='" + id + '\'' +
                ", positionDescription='" + positionDescription + '\'' +
                '}';
    }
}
