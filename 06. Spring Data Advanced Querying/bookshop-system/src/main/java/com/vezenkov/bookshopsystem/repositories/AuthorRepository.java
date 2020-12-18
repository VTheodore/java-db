package com.vezenkov.bookshopsystem.repositories;

import com.vezenkov.bookshopsystem.entitites.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsAuthorByLastName(String lastName);

    boolean existsAuthorByFirstNameAndLastName(String firstName, String lastName);

    List<Author> findAuthorsByFirstNameEndingWith(String substr);

    @Query("SELECT SUM(b.copies) FROM Author a JOIN a.books b WHERE CONCAT(a.firstName, ' ', a.lastName) = :name")
    int getTotalCopiesSoldByFullName(@Param("name") String fullName);

    @Query(value = "CALL GetNumberOfBooksWritten(:firstName, :lastName)", nativeQuery = true)
    Integer getCountOfBooks(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
