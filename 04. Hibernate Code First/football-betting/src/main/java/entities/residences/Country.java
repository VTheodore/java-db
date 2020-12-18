package entities.residences;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "countries")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Country{

    @Size(min = 3, max = 3)
    @Id
    @Column(name = "id", length = 3)
    private String id;

    @NonNull
    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Town> towns;

    @ManyToMany(targetEntity = Continent.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "countries_continents",
            joinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "continent_id", referencedColumnName = "id"))
    private Set<Continent> continents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id.equals(country.id) &&
                name.equals(country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
