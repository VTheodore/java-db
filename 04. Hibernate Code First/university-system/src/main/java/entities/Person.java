package entities;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @NonNull
    @NotNull
    @Column(name = "first_name", length = 50)
    private String firstName;

    @NonNull
    @NotNull
    @Column(name = "last_name", length = 60)
    private String lastName;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;
}
