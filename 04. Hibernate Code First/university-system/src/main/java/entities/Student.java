package entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.util.Set;

@Entity
@Table(name = "students")
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends Person {

    @DecimalMin("2.00")
    @DecimalMax("6.00")
    @Digits(integer = 1, fraction = 2)
    @Column(name = "average_grade")
    private Double averageGrade;

    @ManyToMany
    @JoinTable(name = "students_courses",
    joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id")
    )
    private Set<Course> courses;
}
