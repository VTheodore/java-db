package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "medicaments")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Medicament {

    @Id
    @Column(name = "medicament_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @NotNull
    @Size(min = 3, max = 500)
    @Column(name = "name", length = 500)
    private String name;
}
