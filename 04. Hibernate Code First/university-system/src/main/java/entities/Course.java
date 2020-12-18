package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Course {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @NonNull
    @NotNull
    @Size(min = 3, max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Positive
    @Digits(integer = 2, fraction = 1)
    @Column(name = "credits")
    private Double credits;

    @ManyToMany(mappedBy = "courses", targetEntity = Student.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Student> students;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;
}
