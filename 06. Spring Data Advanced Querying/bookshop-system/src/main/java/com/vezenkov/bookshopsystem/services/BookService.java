package com.vezenkov.bookshopsystem.services;

import com.vezenkov.bookshopsystem.entitites.Book;
import com.vezenkov.bookshopsystem.entitites.EditionType;
import org.springframework.data.repository.query.Param;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface BookService {
    void seedBooks(String filePath) throws IOException, ParseException;

    List<Book> getBooksByAgeRestriction(String ageRestriction);

    List<Book> getBooksByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> getBooksWithPriceLessThanAndPriceGreaterThan(BigDecimal lessThan, BigDecimal higherThan);

    List<Book> getBooksWhichAreNotReleasedInAGivenYear(int year);

    List<Book> getBooksByReleasedDateBefore(String date);

    List<Book> getBooksByTitleContaining(String substr);

    List<Book> getBookByAuthorLastNameStartingWith(String substr);

    long getCountOfBooksWithTitleLengthGreaterThan(int size);

    List<BookProjection> getBookByTitle(String title);

    int increaseBookCopiesByDateAfter(String dateArgs, int copies);

    int removeBooksByCopiesLessThan(int copies);
}
