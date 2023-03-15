package com.example.EnterpriseResourcePlanningTESTS.controllers;

import com.example.EnterpriseResourcePlanningTESTS.entities.User;
import com.example.EnterpriseResourcePlanningTESTS.enums.Role;
import com.example.EnterpriseResourcePlanningTESTS.exceptions.UserNotFoundException;
import com.example.EnterpriseResourcePlanningTESTS.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "Users/register";
    }


    @PostMapping("/register/save")
    public ModelAndView saveUser(@Valid User user, RedirectAttributes redirectAttributes, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return new ModelAndView("Users/register");

        } else {
            userDetailsService.saveUser(user);
            redirectAttributes.addFlashAttribute("message", "The User has been saved successfully!!!");
            return new ModelAndView("redirect:/users");
        }
    }


    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String registerUser(Model model, @Valid User user , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("successMessage", "User not registered!");
            return "Users/register";
        }
        if(userDetailsService.isUserExist(user)){
            model.addAttribute("successMessage", "This username already present!");
            return "Users/register";
        }
        userDetailsService.saveUser(user);
        return "Auth/login";
    }


    @GetMapping
    public String showUsersList(Model model, @Param("keyword") String keyword) {
        List<User> listUsers = userDetailsService.listAll(keyword);
        model.addAttribute("listUsers", listUsers);
        return "Users/users";
    }


    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", Role.values());
        model.addAttribute("pageTitle", "Add New User");
        return "Users/register";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            userDetailsService.delete(id);
            ra.addFlashAttribute("message", "The User with Id: " + id + " has been already deleted!!!");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }


    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            User user = userDetailsService.get(id);
            model.addAttribute("user", user);
            model.addAttribute("listRoles", Role.values());
            model.addAttribute("pageTitle", "Edit a User with (Id: " + id + ")");
            return "Users/register";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }


}
