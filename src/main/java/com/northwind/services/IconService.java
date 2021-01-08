package com.northwind.services;

import com.northwind.entities.Icon;
import com.northwind.repositories.IconRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IconService {

    @Autowired
    private IconRepository iconRepository;

    public List<Icon> getCategories() {
        return iconRepository.findAll();
    }
}
