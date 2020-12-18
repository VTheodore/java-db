package com.vezenkov.bookshopsystem.services;

import com.vezenkov.bookshopsystem.entitites.Author;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface AuthorService {
    void seedAuthors(String filePath) throws IOException;

    long getCount();

    Author getAuthorById(Long id);

    List<Author> getAuthorsWithBookBefore1990() throws ParseException;

    List<Author> getAuthorsOrderedByBookCountDesc();
}
