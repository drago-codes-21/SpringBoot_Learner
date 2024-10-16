package com.LearnMongo.LearnMongo.repository;

import com.LearnMongo.LearnMongo.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
}
