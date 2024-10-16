package com.LearnMongo.LearnMongo.Controller;

import com.LearnMongo.LearnMongo.Entity.Book;
import com.LearnMongo.LearnMongo.Entity.User;
import com.LearnMongo.LearnMongo.Services.BookEntryService;
import com.LearnMongo.LearnMongo.Services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//The @RestController annotation in Spring is a specialized version of the @Controller annotation, used in Spring MVC applications to create RESTful web services. It combines @Controller and @ResponseBody, meaning that it handles web requests and automatically serializes the response body to JSON or XML format.
//Key Features of @RestController
//RESTful Responses: Automatically converts the return values of methods to JSON or XML, depending on the content type requested by the client. This is useful for building RESTful APIs.
// Convenience: Simplifies the creation of RESTful services by eliminating the need to annotate each method with @ResponseBody.
//Mapping HTTP Requests: You can map HTTP requests to handler methods using annotations like @GetMapping, @PostMapping, @PutMapping, and @DeleteMapping.

// URL Mapping: You can specify the URL pattern(s) that the method or class should handle.
//HTTP Methods: It allows you to define which HTTP methods (GET, POST, PUT, DELETE, etc.) a method should respond to.
//Flexible Configuration: You can use various attributes to customize the mapping, including parameters, headers, and media types.
//Class-Level and Method-Level: You can annotate the entire controller class to set a base URL and use method-level annotations to define specific routes.
// Adding Request Mapping above Class will add mentioned mapping for all mappings present in Class/interface
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookEntryService bookEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<List<Book>> getAllUserOwnedBooks(@PathVariable String username) {
        User currentUser = userService.findByUsername(username);
        if(currentUser != null) {
            List<Book> books = currentUser.getOwnedBooks();
            if(books != null && !books.isEmpty())
                return new ResponseEntity<>(books, HttpStatus.OK);
            return new ResponseEntity<>(books, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{username}")
    public ResponseEntity<Book> createEntry (@RequestBody Book book, @PathVariable String username) {

        bookEntryService.saveEntry(book, username);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // {} is not a parameter, it is a path variable
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getSingleEntry (@PathVariable ObjectId myId) {
        Optional<Book> student = bookEntryService.getBookEntry(myId);
        return student.isPresent()
                ? new ResponseEntity<>(bookEntryService.getBookEntry(myId), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{username}/{myId}")
    public  ResponseEntity<?> updateEntry(@PathVariable ObjectId myId, @PathVariable String username, @RequestBody Book newBook) {
        User currentUser = userService.findByUsername(username);
        List<Book> collect = currentUser.getOwnedBooks().stream().filter(x -> x.getId().equals(myId)).toList();
        if(!collect.isEmpty()) {
            Optional<Book> book = bookEntryService.getBookEntry(myId);
            if(book.isPresent()) {
                Book currentBook = book.get();
                currentBook.setTitle(!newBook.getTitle().isEmpty()
                        ? newBook.getTitle() : currentBook.getTitle());
                currentBook.setContent(newBook.getContent() != null && !newBook.getContent().isEmpty()
                        ? newBook.getContent() : currentBook.getContent());
                bookEntryService.updateEntry(currentBook);
                // Don't need to do anything in User because it has a DB reference of this Object.
                return new ResponseEntity<>(currentBook, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{username}/{myId}")
    public ResponseEntity<?> deleteEntry (@PathVariable ObjectId myId, @PathVariable String username) {
        bookEntryService.deleteEntry(myId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
