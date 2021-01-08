package com.northwind.repositories;

import com.northwind.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
    User findByEmail(String email);
}
