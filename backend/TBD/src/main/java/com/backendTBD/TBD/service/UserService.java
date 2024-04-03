package com.backendTBD.TBD.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.backendTBD.TBD.model.User;
import com.backendTBD.TBD.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public ResponseEntity<?> saveUser(User user) {
        Optional<User> userExists = userRepo.findByEmail(user.getEmail());
        if (!userExists.isPresent()) {
            userRepo.save(user);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User with email already exists", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> updateUser(String email, User user) {
        Optional<User> userExists = userRepo.findByEmail(email);
        if (userExists.isPresent()) {
            User existingUser = userExists.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            userRepo.save(existingUser);
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with email " + email + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public boolean deleteUser(Long id) {
        Optional<User> userExists = userRepo.findById(id);
        if (userExists.isPresent()) {
            userRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public ResponseEntity<?> patchUser(String email, User user) {
        Optional<User> userExists = userRepo.findByEmail(email);
        if (userExists.isPresent()) {
            User existingUser = userExists.get();
            if (user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(user.getPassword());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getPhone() != null) {
                existingUser.setPhone(user.getPhone());
            }
            userRepo.save(existingUser);
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with email " + email + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
