package com.api.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.back.model.User;
import com.api.back.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email) != null;
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
