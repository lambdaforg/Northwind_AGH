package com.northwind.services;

import com.northwind.entities.Category;
import com.northwind.entities.Product;
import com.northwind.entities.Supplier;
import com.northwind.repositories.ProductRepository;
import com.northwind.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SupplierService {
    @Autowired
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(int id) {
        return supplierRepository.findFirstById(id);
    }

    public void deleteSupplier(Integer id) {
        supplierRepository.deleteById(id);
    }

    public void updateSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }
}
