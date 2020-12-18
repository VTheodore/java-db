package entities.residences;

import entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "continets")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Continent extends BaseEntity {
    @ManyToMany(mappedBy = "continents", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Country> countries;
}
