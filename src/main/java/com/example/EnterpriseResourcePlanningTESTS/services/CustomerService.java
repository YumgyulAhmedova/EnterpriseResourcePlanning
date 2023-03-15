package com.example.EnterpriseResourcePlanningTESTS.services;

import com.example.EnterpriseResourcePlanningTESTS.entities.Customer;
import com.example.EnterpriseResourcePlanningTESTS.exceptions.UserNotFoundException;
import com.example.EnterpriseResourcePlanningTESTS.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> listAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    public List<Customer> listAll(String keyword) {
        if (keyword != null) {
            return customerRepository.findAll(keyword);
        }
        return (List<Customer>) customerRepository.findAll();
    }


    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer get(Long id) throws UserNotFoundException {
        Optional<Customer> result = customerRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find any Customers with Id: " + id);
    }

    public void delete(Long id) throws UserNotFoundException {
        Long count = customerRepository.countById(id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any Customers with Id: " + id);
        }
        customerRepository.deleteById(id);
    }


    public void addAllCustomers(Model model) {
        Iterable<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
    }




}
