package com.vezenkov.bookshopsystem.repositories;

import com.vezenkov.bookshopsystem.entitites.AgeRestriction;
import com.vezenkov.bookshopsystem.entitites.Book;
import com.vezenkov.bookshopsystem.entitites.EditionType;
import com.vezenkov.bookshopsystem.services.BookProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findBooksByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findBooksByPriceLessThanOrPriceGreaterThan(BigDecimal lessThan, BigDecimal higherThan);

    List<Book> findBooksByReleaseDateBeforeOrReleaseDateAfter(Date before, Date after);

    List<Book> findBooksByReleaseDateBefore(Date date);

    List<Book> findBooksByTitleContaining(String substr);

    @Query("SELECT b FROM Book b JOIN b.author a WHERE a.lastName LIKE CONCAT(:substr, '%') ")
    List<Book> findBooksByAuthorLastNameStartWith(@Param("substr") String substr);

    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :size")
    long findCountOfBooksWhoseTitleSizeGreaterThan(@Param("size") int size);

    List<BookProjection> findBooksByTitle(String title);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.copies = b.copies + :toIncrease WHERE b.releaseDate > :date")
    int increaseBookCopiesByDateAfter(@Param("date") Date date, @Param("toIncrease") int copies);

    @Modifying
    @Transactional
    int removeBooksByCopiesLessThan(int copies);
}
