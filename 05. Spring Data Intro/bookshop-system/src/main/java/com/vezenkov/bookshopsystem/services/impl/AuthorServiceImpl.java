package com.vezenkov.bookshopsystem.services.impl;

import com.vezenkov.bookshopsystem.entitites.Author;
import com.vezenkov.bookshopsystem.repositories.AuthorRepository;
import com.vezenkov.bookshopsystem.services.AuthorService;
import com.vezenkov.bookshopsystem.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final FileUtil fileUtil;

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(FileUtil fileUtil, AuthorRepository authorRepository) {
        this.fileUtil = fileUtil;
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors(String filePath) throws IOException {
        String[] lineArgs = this.fileUtil.readFileContent(filePath);

        for (String line : lineArgs) {
            String[] args = line.split("\\s+");

            if (args.length == 1) {
                String lastName = args[0];

                if (!this.authorRepository.existsAuthorByLastName(lastName)) {
                    Author author = new Author();
                    author.setLastName(lastName);
                    this.authorRepository.saveAndFlush(author);
                }
            }else if (args.length == 2) {
                String firstName = args[0];
                String lastName = args[1];

                if (!this.authorRepository.existsAuthorByFirstNameAndLastName(firstName, lastName)) {
                    Author author = new Author();
                    author.setFirstName(firstName);
                    author.setLastName(lastName);

                    this.authorRepository.saveAndFlush(author);
                }
            }
        }
    }

    @Override
    public long getCount() {
        return this.authorRepository.count();
    }

    @Override
    public Author getAuthorById(Long id) {
        return this.authorRepository.getOne(id);
    }

    @Override
    public List<Author> getAuthorsWithBookBefore1990() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
        Date date = format.parse("1/1/1990");
        return this.authorRepository.getAuthorsByBooksBefore(date);
    }

    @Override
    public List<Author> getAuthorsOrderedByBookCountDesc() {
        return this.authorRepository.getAuthorsOrderedByBookCountDesc();
    }
}
