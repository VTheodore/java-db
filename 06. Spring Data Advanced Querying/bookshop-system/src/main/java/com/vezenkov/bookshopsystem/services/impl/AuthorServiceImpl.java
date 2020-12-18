package com.vezenkov.bookshopsystem.services.impl;

import com.vezenkov.bookshopsystem.entitites.Author;
import com.vezenkov.bookshopsystem.repositories.AuthorRepository;
import com.vezenkov.bookshopsystem.services.AuthorService;
import com.vezenkov.bookshopsystem.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

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
    public Author getRandomAuthor() {
        Random random = new Random();

        return this.authorRepository
                .getOne((long) random.nextInt((int) this.authorRepository.count()) + 1);
    }

    @Override
    public List<Author> getAuthorsWithFirstNameEndingWith(String substr) {
        return this.authorRepository.findAuthorsByFirstNameEndingWith(substr);
    }

    @Override
    public int getAuthorBookCopiesByFullName(String fullName) {
        return this.authorRepository.getTotalCopiesSoldByFullName(fullName);
    }

    @Override
    public Integer getCountOfWrittenBooksByFullName(String fullName) {
        String[] args = fullName.split("\\s+");
        return this.authorRepository.getCountOfBooks(args[0], args[1]);
    }
}
