package com.vezenkov.bookshopsystem.services.impl;

import com.vezenkov.bookshopsystem.entitites.*;
import com.vezenkov.bookshopsystem.repositories.BookRepository;
import com.vezenkov.bookshopsystem.services.AuthorService;
import com.vezenkov.bookshopsystem.services.BookService;
import com.vezenkov.bookshopsystem.services.CategoryService;
import com.vezenkov.bookshopsystem.util.Constants;
import com.vezenkov.bookshopsystem.util.FileUtil;
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

            Author author = this.getRandomAuthor();

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

            Set<Category> categories = this.getRandomCategories();

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
    public List<Book> getAllBooksAfterTheYear2000() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
        Date date = format.parse("31/12/2000");

        return this.bookRepository.getAllByReleaseDateAfter(date);
    }

    @Override
    public List<Book> getAllBooksByGeorgePowell() {
        return this.bookRepository.getAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc("George", "Powell");
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        long authorIndex = random.nextInt((int) this.authorService.getCount()) + 1;
        return this.authorService.getAuthorById(authorIndex);
    }

    private Category getRandomCategory() {
        Random random = new Random();
        long categoryId = random.nextInt((int) this.categoryService.getCount()) + 1;
        return this.categoryService.getCategoryById(categoryId);
    }

    private Set<Category> getRandomCategories() {
        Random random = new Random();
        int categoriesCount = random.nextInt(3) + 1;

        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < categoriesCount; i++) {
            categories.add(this.getRandomCategory());
        }

        return categories;
    }
}
