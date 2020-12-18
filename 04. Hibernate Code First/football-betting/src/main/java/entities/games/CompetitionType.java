package entities.games;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "competition_type")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CompetitionType{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "type", length = 100)
    private String type;
}
