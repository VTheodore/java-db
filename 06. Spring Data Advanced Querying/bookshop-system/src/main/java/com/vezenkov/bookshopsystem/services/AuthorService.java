package com.vezenkov.bookshopsystem.services;


import com.vezenkov.bookshopsystem.entitites.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors(String filePath) throws IOException;

    Author getRandomAuthor();

    List<Author> getAuthorsWithFirstNameEndingWith(String substr);

    int getAuthorBookCopiesByFullName(String fullName);

    Integer getCountOfWrittenBooksByFullName(String fullName);
}
