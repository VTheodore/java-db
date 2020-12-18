package com.vezenkov.bookshopsystem.repositories;

import com.vezenkov.bookshopsystem.entitites.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsAuthorByLastName(String lastName);

    boolean existsAuthorByFirstNameAndLastName(String firstName, String lastName);

    @Query(value = "SELECT * FROM authors AS a JOIN books b on a.id = b.author_id WHERE b.release_date < :date", nativeQuery = true)
    List<Author> getAuthorsByBooksBefore(@Param("date") Date date);

    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> getAuthorsOrderedByBookCountDesc();
}
