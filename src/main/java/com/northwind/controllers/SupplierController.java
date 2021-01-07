package com.northwind.controllers;

import com.northwind.entities.Supplier;
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
    private SupplierService supplierService;

   @PostMapping("/dashboard/addSupplier")
    public ModelAndView addCategory(@ModelAttribute Supplier supplier, Model model) {
        if(supplierService.getSuppliersByName(supplier.getCompanyName()).isEmpty()) {
            supplierService.saveSupplier(new Supplier(
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
    @GetMapping("/dashboard/editSupplier/{id}")
    public String editSupplier(@PathVariable String id, Model model){
        model.addAttribute("supplier", supplierService.getSupplierById(id));
        return "/admin/editSupplier";
    }
    @PostMapping("/dashboard/deleteSupplier/{id}")
    public ModelAndView deleteSupplier(@PathVariable String id, Model model){
        supplierService.deleteSupplier(id);
        return new ModelAndView("redirect:" + "/dashboard/managementAll");
    }
    @PostMapping("/dashboard/saveSupplier")
    public ModelAndView saveSupplier(@ModelAttribute Supplier supplier, Model model) {
        supplierService.saveSupplier(supplier);
        return new ModelAndView("redirect:" + "/dashboard/managementAll");
    }
}

