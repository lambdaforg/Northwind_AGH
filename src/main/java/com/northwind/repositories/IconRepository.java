package com.northwind.repositories;

import com.northwind.entities.Icon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IconRepository extends MongoRepository<Icon, Integer> {
}
