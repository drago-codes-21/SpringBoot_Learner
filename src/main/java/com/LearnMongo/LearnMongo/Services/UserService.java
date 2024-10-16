package com.LearnMongo.LearnMongo.Services;

import com.LearnMongo.LearnMongo.Entity.User;
import com.LearnMongo.LearnMongo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.bson.types.ObjectId;
import java.util.*;

@Component
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public List<User> getAllUsersList() {
        return userRepo.findAll();
    }

    public Optional<User> getUserDetails(ObjectId id) {
        return userRepo.findById(id);
    }

    public void deleteUser(ObjectId id) {
       userRepo.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
