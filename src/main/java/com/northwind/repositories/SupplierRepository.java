package com.northwind.repositories;


import com.northwind.entities.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
public interface SupplierRepository extends MongoRepository<Supplier, Integer> {
    Supplier findFirstById(int supplierId);
    List<Supplier> findAllByCompanyName(String name);
}
