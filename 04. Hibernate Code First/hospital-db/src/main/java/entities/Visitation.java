package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "visitations")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Visitation {

    @Id
    @Column(name = "visitation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NonNull
    @PastOrPresent
    @Column(name = "date")
    private Date date;

    @Size(min = 3, max = 1000)
    @Column(name = "comments", length = 1000)
    private String comments;

    @NonNull
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    private Patient patient;

    @NonNull
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "diagnose_id", referencedColumnName = "diagnose_id")
    private Diagnose diagnose;

    @ManyToOne
    @JoinColumn(name = "prescribed_medicament_id", referencedColumnName = "medicament_id")
    private Medicament prescribedMedicament;
}
