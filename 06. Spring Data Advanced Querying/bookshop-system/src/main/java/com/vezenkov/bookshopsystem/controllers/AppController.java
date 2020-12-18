package com.vezenkov.bookshopsystem.controllers;

import com.vezenkov.bookshopsystem.entitites.AgeRestriction;
import com.vezenkov.bookshopsystem.entitites.Author;
import com.vezenkov.bookshopsystem.entitites.Book;
import com.vezenkov.bookshopsystem.entitites.EditionType;
import com.vezenkov.bookshopsystem.services.AuthorService;
import com.vezenkov.bookshopsystem.services.BookService;
import com.vezenkov.bookshopsystem.services.CategoryService;
import com.vezenkov.bookshopsystem.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.math.BigDecimal;


@Controller
public class AppController implements CommandLineRunner {

    private final AuthorService authorService;

    private final BookService bookService;

    private final CategoryService categoryService;

    private final BufferedReader bufferedReader;

    @Autowired
    public AppController(AuthorService authorService, BookService bookService, CategoryService categoryService, BufferedReader bufferedReader) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        this.categoryService.seedCategories(Constants.CATEGORIES_FILE_PATH);
        this.authorService.seedAuthors(Constants.AUTHORS_FILE_PATH);
        this.bookService.seedBooks(Constants.BOOKS_FILE_PATH);

        //01. Get books by age restriction
//        System.out.print("Enter age restriction: ");
//
//        this.bookService
//                .getBooksByAgeRestriction(this.bufferedReader.readLine())
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);

        //02. Golden Books with less than 5000 copies
//        this.bookService.getBooksByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000)
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);

        //03. Get Books by price lower than 5 and higher than 40
//        this.bookService.getBooksWithPriceLessThanAndPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
//                .forEach(b -> System.out.printf("%s - $%.2f%n", b.getTitle(), b.getPrice()));

        //04. Get books not released in given year
//        System.out.print("Enter year: ");
//        this.bookService.getBooksWhichAreNotReleasedInAGivenYear(Integer.parseInt(this.bufferedReader.readLine()))
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);

        //05. Books Released Before Date
//        System.out.print("Enter date in the format dd/MM/yyyy (eg. 12-04-1992): ");
//        this.bookService.getBooksByReleasedDateBefore(this.bufferedReader.readLine())
//                .forEach(b -> System.out.printf("%s %s %.2f%n", b.getTitle(), b.getEditionType().toString(), b.getPrice()));

        //06. Authors Search
//        System.out.print("Enter substring to search: ");
//        this.authorService.getAuthorsWithFirstNameEndingWith(this.bufferedReader.readLine())
//                .forEach(a -> System.out.printf("%s %s%n", a.getFirstName(), a.getLastName()));

        //07. Books Search
//        System.out.print("Enter substr to search for books: ");
//        this.bookService.getBooksByTitleContaining(this.bufferedReader.readLine())
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);

        //08. Get Books written by authors whose last name starts with a given string
//        System.out.print("Enter substring to search: ");
//        this.bookService.getBookByAuthorLastNameStartingWith(this.bufferedReader.readLine())
//                .forEach(b -> System.out.printf("%s (%s %s)%n", b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()));

        //09. Count of books with title longer than given number
//        System.out.print("Enter title length: ");
//        System.out.println(this.bookService.getCountOfBooksWithTitleLengthGreaterThan(Integer.parseInt(this.bufferedReader.readLine())));

        //10. Total book copies
//        System.out.print("Enter authors' full name: ");
//        String fullName = this.bufferedReader.readLine();
//        int totalCopies = this.authorService.getAuthorBookCopiesByFullName(fullName);
//        System.out.printf("%s - %d%n", fullName, totalCopies);

        //11. Reduced book
//        System.out.print("Enter book title: ");
//        this.bookService.getBookByTitle(this.bufferedReader.readLine())
//                .forEach(b -> System.out.printf("%s %s %s %.2f%n", b.getTitle(), b.getEditionType().toString(), b.getAgeRestriction().toString(), b.getPrice()));

        //12. Increase book copies
//        System.out.print("Enter date in format dd MMM yyyy (eg. 12 Oct 2005): ");
//        String dateArgs = this.bufferedReader.readLine();
//        System.out.print("Enter number of copies to be added: ");
//        int copies = Integer.parseInt(this.bufferedReader.readLine());
//
//        int rowsAffected = this.bookService.increaseBookCopiesByDateAfter(dateArgs, copies);
//        System.out.println(rowsAffected * copies);

        //13. Remove Books
//        System.out.print("Enter number of copies: ");
//        System.out.println(this.bookService.removeBooksByCopiesLessThan(Integer.parseInt(this.bufferedReader.readLine())));

        //14. Stored Procedure
        System.out.print("Enter full name of author: ");
        System.out.println(this.authorService.getCountOfWrittenBooksByFullName(this.bufferedReader.readLine()));
    }
}
