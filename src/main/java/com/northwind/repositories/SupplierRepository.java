package com.northwind.repositories;


import com.northwind.entities.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
public interface SupplierRepository extends MongoRepository<Supplier, String> {
    Supplier findFirstById(String supplierId);
    List<Supplier> findAllByCompanyName(String name);
}
