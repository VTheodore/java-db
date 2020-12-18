package com.vezenkov.bookshopsystem.services;

import com.vezenkov.bookshopsystem.entitites.Book;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface BookService {
    void seedBooks(String filePath) throws IOException, ParseException;

    List<Book> getAllBooksAfterTheYear2000() throws ParseException;

    List<Book> getAllBooksByGeorgePowell();
}
