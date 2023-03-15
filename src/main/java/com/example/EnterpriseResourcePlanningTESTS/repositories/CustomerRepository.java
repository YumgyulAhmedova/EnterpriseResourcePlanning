package com.example.EnterpriseResourcePlanningTESTS.repositories;

import com.example.EnterpriseResourcePlanningTESTS.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Long countById(Long id);

    @Query("SELECT c FROM Customer c WHERE c.firstName LIKE %:name% ")
    public List<Customer> searchByPartialName(String name);

    @Query("SELECT c FROM Customer c WHERE " + "CONCAT(c.id, c.firstName, c.middleName, c.lastName, c.projectName, c.type, c.contractExpirationDate)" + "LIKE %?1%")  /// c.contractExpirationDate
    public List<Customer> findAll(String keyword);

}
