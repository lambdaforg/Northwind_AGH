package com.northwind.services;

import com.northwind.entities.Icon;
import com.northwind.repositories.IconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IconService {

    @Autowired
    private IconRepository iconRepository;

    public List<Icon> getIcons() {
        return iconRepository.findAll();
    }
}
