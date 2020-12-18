package entities.player;

import entities.BaseEntity;
import entities.misc.Color;
import entities.residences.Town;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "teams")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Team extends BaseEntity {
    @Lob
    @Column(name = "logo", columnDefinition = "BLOB")
    private byte[] logo;

    @NonNull
    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "initials", length = 3)
    private String initials;

    @NonNull
    @NotNull
    @ManyToOne
    @JoinColumn(name = "primary_kit_color", referencedColumnName = "id")
    private Color primaryKitColor;

    @NonNull
    @NotNull
    @ManyToOne
    @JoinColumn(name = "secondary_kit_color", referencedColumnName = "id")
    private Color secondaryKitColor;

    @NonNull
    @NotNull
    @ManyToOne(targetEntity = Town.class)
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    @NonNull
    @NotNull
    @Positive
    @Column(name = "budget")
    private BigDecimal budget;

    @OneToMany(mappedBy = "team", targetEntity = Player.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Player> players;
}
