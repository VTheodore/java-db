package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "diagnoses")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Diagnose {

    @Id
    @Column(name = "diagnose_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 1000)
    @Column(name = "comments", length = 1000)
    private String comments;

    @ManyToMany(mappedBy = "diagnoses", targetEntity = Patient.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Patient> patients;
}
