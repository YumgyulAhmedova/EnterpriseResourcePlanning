package com.example.EnterpriseResourcePlanningTESTS.controllers;

import com.example.EnterpriseResourcePlanningTESTS.entities.Customer;
import com.example.EnterpriseResourcePlanningTESTS.entities.Protocol;
import com.example.EnterpriseResourcePlanningTESTS.entities.User;
import com.example.EnterpriseResourcePlanningTESTS.exceptions.ProtocolNotFoundException;
import com.example.EnterpriseResourcePlanningTESTS.services.CustomerService;
import com.example.EnterpriseResourcePlanningTESTS.services.ProtocolService;
import com.example.EnterpriseResourcePlanningTESTS.services.SearchService;
import com.example.EnterpriseResourcePlanningTESTS.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/protocols")
public class ProtocolController {

    @Autowired
    private ProtocolService protocolService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private SearchService searchService;

    @GetMapping()
    private String getAllProtocols(Model model) {
        String keyword = null;
        return listByPage(model, 1, "name", "asc", keyword);
    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
                             @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword) {
        Page<Protocol> page = protocolService.listAll(currentPage, sortField, sortDir, keyword);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Protocol> listProtocols = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listProtocols", listProtocols);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);

        int total = searchService.calculateTotalHours(listProtocols);
        model.addAttribute("total", total);

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("reverseSortDir", reverseSortDir);

        return "Protocols/protocols";
    }


    @GetMapping("/new")
    public String showNewProtocolForm(Model model) {
        List<Customer> customerList = customerService.listAll();
        model.addAttribute("protocol", new Protocol());
        model.addAttribute("customerList", customerList);
        Iterable<User> userList = userService.listAll();
        model.addAttribute("userList", userList);
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("pageTitle", "Add New Protocol");
        return "Protocols/protocol_form";
    }


    @PostMapping("/save")
    public ModelAndView saveProtocol(@Valid Protocol protocol, RedirectAttributes redirectAttributes, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            customerService.addAllCustomers(model);
            return new ModelAndView("/protocols/new");

        } else {
            protocolService.save(protocol);
            redirectAttributes.addFlashAttribute("message", "The Protocol has been saved successfully!!!");
            return new ModelAndView("redirect:/protocols");
        }
    }


    @GetMapping("/edit/{id}")
    public String showEditProtocolForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            Protocol protocol = protocolService.get(id);
            model.addAttribute("protocol", protocol);
            List<Customer> customerList = customerService.listAll();
            model.addAttribute("customerList", customerList);
            Iterable<User> userList = userService.listAll();
            model.addAttribute("userList", userList);
            model.addAttribute("pageTitle", "Edit a Protocol with (Id: " + id + ")");
            return "Protocols/protocol_form";
        } catch (ProtocolNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/protocols";
        }

    }

    @GetMapping("/delete/{id}")
    public String deleteProtocol(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            protocolService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The Protocol with Id: " + id + " has been already deleted!!!");
        } catch (ProtocolNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/protocols";
    }


}
