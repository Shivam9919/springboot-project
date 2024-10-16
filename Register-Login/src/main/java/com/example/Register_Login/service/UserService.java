// UserService.java
package com.example.Register_Login.service;

import com.example.Register_Login.model.User;
import com.example.Register_Login.repo.UserRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private Map<String, User> users = new HashMap<>();
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already in use.");
        }

        user.setUsername(generateUniqueUsername(user.getUsername()));
        return userRepository.save(user);
    }

    private String generateUniqueUsername(String baseUsername) {
        String username = baseUsername;
        int count = 1;

        while (userRepository.findByUsername(username) != null) {
            username = baseUsername + count;
            count++;
        }
        return username;
    }

    public User loginUser(String identifier, String password) {
        User user = userRepository.findByUsername(identifier);
        if (user == null) {
            user = userRepository.findByEmail(identifier);
        }
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void logoutUser(String username) {
        User user = users.get(username);
        if (user != null) {
            user.setLoggedIn(false);
        }
    }

    public Optional<User> updateUser(Long userId, User updatedUser) {
        return userRepository.findById(userId).map(user -> {
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        });
    }

    public void resetPassword(Long userId, String newPassword) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setPassword(newPassword);
            userRepository.save(user);
        });
    }
}
