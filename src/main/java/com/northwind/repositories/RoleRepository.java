package com.northwind.repositories;

import com.northwind.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, Integer> {
    Role findByRole(String role);
}