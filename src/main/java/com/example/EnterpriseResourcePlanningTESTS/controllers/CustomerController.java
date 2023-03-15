package com.example.EnterpriseResourcePlanningTESTS.controllers;

import com.example.EnterpriseResourcePlanningTESTS.entities.Customer;
import com.example.EnterpriseResourcePlanningTESTS.exceptions.UserNotFoundException;
import com.example.EnterpriseResourcePlanningTESTS.services.CustomerService;
import com.example.EnterpriseResourcePlanningTESTS.enums.Type;
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
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping
    public String showCustomersList(Model model, @Param("keyword") String keyword) {
        List<Customer> listCustomers = customerService.listAll(keyword);
        model.addAttribute("listCustomers", listCustomers);
        return "/Customers/customers";
    }



    @GetMapping("/new")
    public String showNewCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("listTypes", Type.values());
        model.addAttribute("pageTitle", "Add New Customer");
        return "/Customers/customer_form";
    }


    @PostMapping("/save")
    public ModelAndView saveCustomer(@Valid Customer customer, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/customers/new");
        } else {
            customerService.save(customer);
            redirectAttributes.addFlashAttribute("message", "The Customer has been saved successfully!!!");
            return new ModelAndView("redirect:/customers");
        }
    }


    @GetMapping("/edit/{id}")
    public String showEditCustomerForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Customer customer = customerService.get(id);
            model.addAttribute("customer", customer);
            model.addAttribute("listTypes", Type.values());
            model.addAttribute("pageTitle", "Edit a Customer with (Id: " + id + ")");
            return "/Customers/customer_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/customers";
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            customerService.delete(id);
            ra.addFlashAttribute("message", "The Customer with Id: " + id + " has been already deleted!!!");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/customers";
    }





}
