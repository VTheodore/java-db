package com.vezenkov.bookshopsystem.entitites;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull
    @ManyToOne(targetEntity = Author.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @NonNull
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @NonNull
    @NotNull
    @Enumerated
    @Column(name = "edition_type")
    private EditionType editionType;

    @Positive
    @Column(name = "price")
    private BigDecimal price;

    @NonNull
    @NotNull
    @PositiveOrZero
    @Column(name = "copies")
    private int copies;

    @PastOrPresent
    @Column(name = "release_date")
    private Date releaseDate;

    @NonNull
    @NotNull
    @Enumerated
    @Column(name = "age_restriction")
    private AgeRestriction ageRestriction;

    @ManyToMany(targetEntity = Category.class, cascade = CascadeType.ALL)
    @JoinTable(name = "books_categories",
        joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories =  new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return copies == book.copies &&
                Objects.equals(id, book.id) &&
                title.equals(book.title) &&
                Objects.equals(description, book.description) &&
                editionType == book.editionType &&
                price.equals(book.price) &&
                Objects.equals(releaseDate, book.releaseDate) &&
                ageRestriction == book.ageRestriction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, editionType, price, copies, releaseDate, ageRestriction);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", editionType=" + editionType +
                ", price=" + price +
                ", copies=" + copies +
                ", releaseDate=" + releaseDate +
                ", ageRestriction=" + ageRestriction +
                '}';
    }
}
