package com.LearnMongo.LearnMongo.repository;

import com.LearnMongo.LearnMongo.Entity.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookEntryRepo extends MongoRepository<Book, ObjectId> {
}
