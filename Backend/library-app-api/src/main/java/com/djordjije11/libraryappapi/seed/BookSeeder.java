package com.djordjije11.libraryappapi.seed;

import com.djordjije11.libraryappapi.helper.random.util.RandomUtil;
import com.djordjije11.libraryappapi.model.*;
import com.djordjije11.libraryappapi.repository.AuthorRepository;
import com.djordjije11.libraryappapi.repository.BookCopyRepository;
import com.djordjije11.libraryappapi.repository.BookRepository;
import com.djordjije11.libraryappapi.repository.PublisherRepository;
import com.github.javafaker.Faker;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Component
public class BookSeeder {
    private final InputStream BOOKS_DATASET_PATH = getClass().getClassLoader().getResourceAsStream("seed\\books.csv");

    private final Faker faker;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final Random random;
    private final List<Publisher> publishers;
    private final List<Author> authors;
    private List<Building> buildings;
    private final List<BookCopy> bookCopies;

    public BookSeeder(Faker faker, PublisherRepository publisherRepository, AuthorRepository authorRepository, BookRepository bookRepository, BookCopyRepository bookCopyRepository) {
        this.faker = faker;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.random = new Random();
        this.publishers = new LinkedList<>();
        this.authors = new LinkedList<>();
        this.bookCopies = new LinkedList<>();
    }

    public List<BookCopy> seed(List<Building> buildings, int publishers, int books, int bookCopiesMax) throws CsvValidationException, IOException {
        this.buildings = buildings;
        seedPublishers(publishers);
        seedBooks(books, bookCopiesMax);
        return bookCopies;
    }

    private Author seedAuthor(String authorName) {
        String[] authorNames = authorName.split(" ", 2);
        String firstname = null;
        String lastname;
        if (authorNames.length == 1) {
            lastname = authorNames[0];
        } else {
            firstname = authorNames[0];
            lastname = authorNames[1];
        }
        var author = new Author(firstname, lastname, getGeneratedParagraphs());
        var authorOpt = authors.stream()
                .filter(
                        a ->
                                (
                                        (a.getFirstname() == null && author.getFirstname() == null)
                                                || (a.getFirstname() != null && a.getFirstname().equals(author.getFirstname()))
                                )
                                        &&
                                        (
                                                (a.getLastname() == null && author.getLastname() == null)
                                                        || (a.getLastname() != null && a.getLastname().equals(author.getLastname())))
                )
                .findFirst();
        if (authorOpt.isPresent()) {
            return authorOpt.get();
        }
        authorRepository.save(author);
        authors.add(author);
        return author;
    }

    private List<Author> seedAuthors(String authors) {
        List<Author> authorList = new LinkedList<>();
        String[] authorsNames = authors.split("/");
        for (String authorName :
                authorsNames) {
            authorList.add(seedAuthor(authorName));
        }
        return authorList;
    }

    private Book seedBook(String title, String pagesNumber, List<Author> authors) {
        var book = new Book();
        book.setTitle(title);
        book.setDescription(getGeneratedParagraphs());
        try {
            book.setPagesNumber(Integer.parseInt(pagesNumber));
        } catch (NumberFormatException ignored) {
        }
        book.setPublisher(RandomUtil.getOne(random, publishers));
        book.setAuthors(authors);
        return bookRepository.save(book);
    }

    private String generateISBN() {
        //978-86-7346-581-4
        return String.format("%s-%s-%s-%s-%s",
                RandomUtil.getDigitsString(random, 3),
                RandomUtil.getDigitsString(random, 2),
                RandomUtil.getDigitsString(random, 4),
                RandomUtil.getDigitsString(random, 3),
                RandomUtil.getDigitsString(random, 1)
        );
    }

    private void seedBookCopy(Book book) {
        var bookCopy = new BookCopy(generateISBN(), BookCopyStatus.AVAILABLE, book, RandomUtil.getOne(random, buildings));
        bookCopyRepository.save(bookCopy);
        bookCopies.add(bookCopy);
    }

    private void seedBookCopies(int count, Book book) {
        for (int i = 0; i < count; i++) {
            seedBookCopy(book);
        }
    }

    private void seedBooks(int count, int bookCopiesMaxCount) throws IOException, CsvValidationException {
        int counter = 0;
        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(BOOKS_DATASET_PATH))
                .withSkipLines(1)
                .build();
        String[] record;
        while (counter < count && (record = csvReader.readNext()) != null) {
            if (random.nextBoolean()) {
                continue;
            }
            String authors = record[2];
            List<Author> authorList = seedAuthors(authors);
            String title = record[1];
            String pagesNumber = record[7];
            Book book = seedBook(title, pagesNumber, authorList);
            seedBookCopies(random.nextInt(bookCopiesMaxCount), book);
            counter++;
        }
    }

    private String getGeneratedParagraphs() {
        return String.join("\n", faker.lorem().paragraphs(random.nextInt(5)));
    }

    private void generatePublisher() {
        var publisher = new Publisher(faker.book().publisher());
        publisherRepository.save(publisher);
        publishers.add(publisher);
    }

    private void seedPublishers(int count) {
        for (int i = 0; i < count; i++) {
            generatePublisher();
        }
    }
}
