package com.northwind.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping()
    public String getHome(){
        return "base";
    }

    /** TYMCZASOWO WSZYSTKIE ROUTE W TYM KONTROLERZE,
     * jeśli robisz swoja cześć widoku to przenieś do odpowiedniego kontrolera
     */
    @GetMapping("/signup")
    public String getRegistrationView(){
        return "account/signup";
    }
    @GetMapping("/signin")
    public String getLoginView(){
        return "account/signin";
    }
    @GetMapping("/reports")
    public String getReports(){
        return "reports/home";
    }



     /*   @GetMapping("/home")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }*/

}


