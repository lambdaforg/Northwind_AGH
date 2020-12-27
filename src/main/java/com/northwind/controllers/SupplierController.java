package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.entities.Supplier;
import com.northwind.repositories.CategoryRepository;
import com.northwind.repositories.SupplierRepository;
import com.northwind.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SupplierController {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private SupplierRepository supplierRepository;

   @PostMapping("/addSupplier")
    public ModelAndView addCategory(@ModelAttribute Supplier supplier, Model model) {
        if(supplierRepository.findAllByCompanyName(supplier.getCompanyName()).isEmpty()) {
            supplierRepository.save(new Supplier(
                    sequenceGeneratorService.generateSequence(Supplier.SEQUENCE_NAME),
                    supplier.companyName,
                    supplier.contactName,
                    supplier.contactTitle,
                    supplier.address,
                    supplier.city,
                    supplier.region,
                    supplier.postalCode,
                    supplier.country,
                    supplier.phone,
                    supplier.fax,
                    supplier.homepage
            ));
        }
        return new ModelAndView("redirect:" + "adminpanel");
    }
}

