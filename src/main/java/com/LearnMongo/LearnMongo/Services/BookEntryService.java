package com.LearnMongo.LearnMongo.Services;

import com.LearnMongo.LearnMongo.Entity.Book;
import com.LearnMongo.LearnMongo.Entity.User;
import com.LearnMongo.LearnMongo.repository.BookEntryRepo;
import com.LearnMongo.LearnMongo.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//The @Component annotation in Spring is a fundamental annotation used to indicate that a class is a Spring-managed component. It is part of the broader stereotype annotations in Spring, which also include @Service, @Repository, and @Controller. These annotations help Spring's component scanning mechanism to identify and register beans in the application context.
//Spring will treat below class like a bean.
@Component
public class BookEntryService {

    @Autowired
    private BookEntryRepo bookEntryRepo;

    @Autowired
    private UserService userService;

    public void saveEntry(Book book, String username) {

        Book savedBook = bookEntryRepo.save(book);

        User currentUser = userService.findByUsername(username);
        List<Book> currentBookList = currentUser.getOwnedBooks();
        currentBookList.add(savedBook);
        currentUser.setOwnedBooks(new ArrayList<>(currentBookList));
        userService.saveUser(currentUser);
    }

    public List<Book> getAllEntries() {
        return bookEntryRepo.findAll();
    }

    public Optional<Book> getBookEntry(ObjectId id) {
        return bookEntryRepo.findById(id);
    }

    public void deleteEntry(ObjectId id) {
        bookEntryRepo.deleteById(id);
    }
}
