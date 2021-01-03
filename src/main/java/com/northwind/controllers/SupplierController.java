package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.entities.Supplier;
import com.northwind.repositories.CategoryRepository;
import com.northwind.repositories.SupplierRepository;
import com.northwind.services.SequenceGeneratorService;
import com.northwind.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SupplierController {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private SupplierService supplierService;

   @PostMapping("/addSupplier")
    public ModelAndView addCategory(@ModelAttribute Supplier supplier, Model model) {
        if(supplierService.getSuppliersByName(supplier.getCompanyName()).isEmpty()) {
            supplierService.saveSupplier(new Supplier(
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
    @GetMapping("/editSupplier/{id}")
    public String editSupplier(@PathVariable Integer id, Model model){
        model.addAttribute("supplier", supplierService.getSupplierById(id));
        return "/admin/editSupplier";
    }
    @PostMapping("/deleteSupplier/{id}")
    public ModelAndView deleteSupplier(@PathVariable Integer id, Model model){
        supplierService.deleteSupplier(id);
        return new ModelAndView("redirect:" + "/managementAll");
    }
    @PostMapping("/saveSupplier")
    public ModelAndView saveSupplier(@ModelAttribute Supplier supplier, Model model) {
        supplierService.saveSupplier(supplier);
        return new ModelAndView("redirect:" + "/managementAll");
    }
}

