package com.LearnMongo.LearnMongo.Entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "User")
@Data
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;

    @DBRef
    //The @DBRef annotation in Spring Boot (specifically with Spring Data MongoDB) is used to define a reference to another document in a MongoDB collection. This is useful for establishing relationships between different entities in a NoSQL database.
    private List<Book> OwnedBooks = new ArrayList<>();
}
