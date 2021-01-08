package com.northwind.controllers;

import com.northwind.entities.User;
import com.northwind.handlers.ProductRequest;
import com.northwind.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private CustomUserDetailsService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("account/login");
        return modelAndView;
    }
//    @GetMapping("/login")
//    public String editCategory(Model model){
//        //model.addAttribute("findProducts", new ProductRequest());
//        return "account/login";
//    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("account/signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("account/signup");
        } else {
            /*Prefix!! ROLE_ */
            if(user.getEmail().equals("admin@gmail.com")){
                userService.saveUser(user, "ROLE_ADMIN");
            } else {
                userService.saveUser(user, "ROLE_USER");
            }
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("account/login");

        }
        return modelAndView;
    }

//    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
//    public ModelAndView dashboard() {
//        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByEmail(auth.getName());
//        modelAndView.addObject("currentUser", user);
//        modelAndView.addObject("fullName", "Welcome " + user.getFullName());
//        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
//        modelAndView.setViewName("account/dashboard");
//        return modelAndView;
//    }
//    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
//    public ModelAndView home() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("home");
//        return modelAndView;
//    }
}
