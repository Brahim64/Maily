package com.api.back.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.api.back.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

}
