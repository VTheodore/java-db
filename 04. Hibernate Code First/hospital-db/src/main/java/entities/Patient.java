package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Patient {

    @Id
    @Column(name = "patient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min = 3, max = 60)
    @Column(name = "last_name", length = 60)
    private String lastName;

    @NonNull
    @NotNull
    @Size(min = 5, max = 400)
    @Column(name = "address", length = 400)
    private String address;

    @NonNull
    @NotNull
    @Email
    @Column(name = "email")
    private String email;

    @NonNull
    @NotNull
    @Past
    @Column(name = "birth_date")
    private Date birthDate;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @NonNull
    @Column(name = "has_insurance")
    private boolean hasInsurance;

    @ManyToMany(targetEntity = Medicament.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "patients_medicaments",
    joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "patient_id"),
    inverseJoinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "medicament_id"))
    private Set<Medicament> medicament;

    @ManyToMany(targetEntity = Diagnose.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "patients_diagnoses",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "diagnose_id"))
    private Set<Diagnose> diagnoses;

    @OneToMany(mappedBy = "patient", targetEntity = Visitation.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Visitation> visitations;
}
