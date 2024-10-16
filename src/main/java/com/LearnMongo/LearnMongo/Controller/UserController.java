package com.LearnMongo.LearnMongo.Controller;

import com.LearnMongo.LearnMongo.Entity.User;
import com.LearnMongo.LearnMongo.Services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers () {
        return new ArrayList<>(userService.getAllUsersList());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
//                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getUser(@PathVariable ObjectId myId) {
        Optional<User> user = userService.getUserDetails(myId);
        return user.isPresent()
                ? new ResponseEntity<>(userService.getUserDetails(myId), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteUserEntry(@PathVariable ObjectId myId) {
        userService.deleteUser(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{myUsername}")
    public ResponseEntity<?> updateUserEntry(@RequestBody User user, @PathVariable String myUsername) {
        User current_user = userService.findByUsername(myUsername);
        if(current_user != null) {
            current_user.setUsername(user.getUsername());
            current_user.setPassword(user.getPassword());
            userService.saveUser(current_user);
            return new ResponseEntity<>(current_user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
