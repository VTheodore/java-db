package entities.residences;

import entities.BaseEntity;
import entities.player.Team;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "towns")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Town extends BaseEntity {
    @ManyToOne(targetEntity = Country.class)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @OneToMany(mappedBy = "town", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Team> teams;
}
