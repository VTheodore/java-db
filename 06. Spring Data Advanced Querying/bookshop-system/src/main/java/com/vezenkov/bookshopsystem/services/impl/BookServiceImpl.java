package com.vezenkov.bookshopsystem.services.impl;

import com.vezenkov.bookshopsystem.entitites.*;
import com.vezenkov.bookshopsystem.repositories.BookRepository;
import com.vezenkov.bookshopsystem.services.AuthorService;
import com.vezenkov.bookshopsystem.services.BookProjection;
import com.vezenkov.bookshopsystem.services.BookService;
import com.vezenkov.bookshopsystem.services.CategoryService;
import com.vezenkov.bookshopsystem.util.Constants;
import com.vezenkov.bookshopsystem.util.FileUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final FileUtil fileUtil;

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final CategoryService categoryService;

    @Autowired
    public BookServiceImpl(FileUtil fileUtil, BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.fileUtil = fileUtil;
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }


    @Override
    public void seedBooks(String filePath) throws IOException, ParseException {
        String[] lineArgs = this.fileUtil.readFileContent(filePath);

        for (String line : lineArgs) {
            String[] data = Arrays.stream(line.split("\\s+"))
                    .filter(w -> !Constants.EMPTY_STRING.equals(w))
                    .toArray(String[]::new);

            Author author = this.authorService.getRandomAuthor();

            EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];

            SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
            Date releaseDate = formatter.parse(data[1]);

            int copies = Integer.parseInt(data[2]);

            BigDecimal price = new BigDecimal(data[3]);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];

            StringBuilder titleBuilder = new StringBuilder();

            for (int i = 5; i < data.length; i++) {
                titleBuilder.append(data[i]).append(" ");
            }

            titleBuilder.deleteCharAt(titleBuilder.length() - 1);
            String title = titleBuilder.toString();

            Set<Category> categories = this.categoryService.getRandomCategories();

            Book book = new Book();
            book.setAuthor(author);
            book.setEditionType(editionType);
            book.setReleaseDate(releaseDate);
            book.setCopies(copies);
            book.setPrice(price);
            book.setAgeRestriction(ageRestriction);
            book.setTitle(title);
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<Book> getBooksByAgeRestriction(String ageRestriction) {
        return this.bookRepository.findBooksByAgeRestriction(AgeRestriction.valueOf(ageRestriction.toUpperCase()));
    }

    @Override
    public List<Book> getBooksByEditionTypeAndCopiesLessThan(EditionType editionType, int copies) {
        return this.bookRepository.findBooksByEditionTypeAndCopiesLessThan(editionType, copies);
    }

    @Override
    public List<Book> getBooksWithPriceLessThanAndPriceGreaterThan(BigDecimal lessThan, BigDecimal higherThan) {
        return this.bookRepository.findBooksByPriceLessThanOrPriceGreaterThan(lessThan, higherThan);
    }

    @SneakyThrows
    @Override
    public List<Book> getBooksWhichAreNotReleasedInAGivenYear(int year) {
        SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");

        Date before = format.parse("01/01/" + year);
        Date after = format.parse("31/12/" + year);


        return this.bookRepository.findBooksByReleaseDateBeforeOrReleaseDateAfter(before, after);
    }

    @SneakyThrows
    @Override
    public List<Book> getBooksByReleasedDateBefore(String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = format.parse(dateFormat);

        return this.bookRepository.findBooksByReleaseDateBefore(date);
    }

    @Override
    public List<Book> getBooksByTitleContaining(String substr) {
        return this.bookRepository.findBooksByTitleContaining(substr);
    }

    @Override
    public List<Book> getBookByAuthorLastNameStartingWith(String substr) {
        return this.bookRepository.findBooksByAuthorLastNameStartWith(substr);
    }

    @Override
    public long getCountOfBooksWithTitleLengthGreaterThan(int size) {
        return this.bookRepository.findCountOfBooksWhoseTitleSizeGreaterThan(size);
    }

    @Override
    public List<BookProjection> getBookByTitle(String title) {
        return this.bookRepository.findBooksByTitle(title);
    }


    @SneakyThrows
    @Override
    public int increaseBookCopiesByDateAfter(String dateArgs, int copies) {
        String dateFormat = String.join("-", dateArgs.split("\\s+"));
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = format.parse(dateFormat);
        return this.bookRepository.increaseBookCopiesByDateAfter(date, copies);
    }

    @Override
    public int removeBooksByCopiesLessThan(int copies) {
        return this.bookRepository.removeBooksByCopiesLessThan(copies);
    }
}
