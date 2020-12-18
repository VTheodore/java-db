package com.vezenkov.bookshopsystem.entitites;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@NamedStoredProcedureQuery(name = "Author.GetCountOfBooks", procedureName = "GetNumberOfBooksWritten", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "first_name", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "last_name", type = String.class)
})
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @NonNull
    @NotNull
    @Size(max = 50)
    @Column(name = "last_name", length = 60, nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "author", targetEntity = Book.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) &&
                Objects.equals(firstName, author.firstName) &&
                lastName.equals(author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
