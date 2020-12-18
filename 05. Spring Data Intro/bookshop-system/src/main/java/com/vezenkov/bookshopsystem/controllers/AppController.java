package com.vezenkov.bookshopsystem.controllers;

import com.vezenkov.bookshopsystem.entitites.Author;
import com.vezenkov.bookshopsystem.services.AuthorService;
import com.vezenkov.bookshopsystem.services.BookService;
import com.vezenkov.bookshopsystem.services.CategoryService;
import com.vezenkov.bookshopsystem.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;



@Controller
public class AppController implements CommandLineRunner {

    private final AuthorService authorService;

    private final BookService bookService;

    private final CategoryService categoryService;

    @Autowired
    public AppController(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.categoryService.seedCategories(Constants.CATEGORIES_FILE_PATH);
        this.authorService.seedAuthors(Constants.AUTHORS_FILE_PATH);
//        this.bookService.seedBooks(Constants.BOOKS_FILE_PATH);

        //01
//        this.bookService.getAllBooksAfterTheYear2000().forEach(book -> System.out.println(book.getTitle()));

        //02
//        this.authorService.getAuthorsWithBookBefore1990()
//                .forEach(a -> System.out.printf("%s %s%n", a.getFirstName(), a.getLastName()));

        //03
//        this.authorService.getAuthorsOrderedByBookCountDesc()
//                .forEach(a -> System.out.printf("%s %s %d%n", a.getFirstName(), a.getLastName(), a.getBooks().size()));

        //04
        this.bookService.getAllBooksByGeorgePowell()
                .forEach(b -> System.out.printf("%s %s %d%n", b.getTitle(), b.getReleaseDate().toString(), b.getCopies()));
    }
}
