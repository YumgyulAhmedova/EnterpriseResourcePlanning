package com.example.EnterpriseResourcePlanning;

import com.example.EnterpriseResourcePlanningTESTS.entities.Customer;
import com.example.EnterpriseResourcePlanningTESTS.exceptions.UserNotFoundException;
import com.example.EnterpriseResourcePlanningTESTS.repositories.CustomerRepository;
import com.example.EnterpriseResourcePlanningTESTS.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private List<Customer> customers;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setMiddleName("John");
        customer.setLastName("John");
        customer.setProjectName("John Project");

        customers = new ArrayList<>();
        customers.add(customer);
    }

    @Test
    void testListAll() {
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.listAll();

        assertEquals(customers, result);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testListAllWithKeyword() {
        String keyword = "John";
        when(customerRepository.findAll(keyword)).thenReturn(customers);

        List<Customer> result = customerService.listAll(keyword);

        assertEquals(customers, result);
        verify(customerRepository, times(1)).findAll(keyword);
    }

    @Test
    void testListAllWithNullKeyword() {
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.listAll(null);

        assertEquals(customers, result);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        customerService.save(customer);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testGet() throws UserNotFoundException {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.get(1L);

        assertEquals(customer, result);
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetWithNonExistentId() {
        Long id = 2L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> customerService.get(id));
        verify(customerRepository, times(1)).findById(id);
    }

    @Test
    void testDelete() throws UserNotFoundException {
        Long id = 1L;
        when(customerRepository.countById(id)).thenReturn(1L);

        customerService.delete(id);

        verify(customerRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteWithNonExistentId() {
        Long id = 2L;
        when(customerRepository.countById(id)).thenReturn(0L);

        assertThrows(UserNotFoundException.class, () -> customerService.delete(id));
        verify(customerRepository, times(1)).countById(id);
    }




}