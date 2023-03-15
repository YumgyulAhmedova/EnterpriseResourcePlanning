package com.example.EnterpriseResourcePlanningTESTS.repositories;

import com.example.EnterpriseResourcePlanningTESTS.entities.Protocol;
import com.example.EnterpriseResourcePlanningTESTS.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProtocolRepository extends PagingAndSortingRepository<Protocol, Long> {
    Long countById(Long id);

    public List<Protocol> findByName(String name);

    @Query("SELECT p FROM Protocol p WHERE p.name LIKE %:name% ")
    public List<Protocol> searchByPartialName(String name);

    @Query("SELECT p FROM Protocol p WHERE " + " CONCAT(p.id, ' ', p.user.username, ' ', p.name, ' ', p.hours, ' ', p.customer.firstName, ' ', p.customer.middleName, ' ', p.customer.lastName, ' ', p.customer.projectName, ' ', p.customer.contractExpirationDate, ' ', p.dateOfCreate, ' ', p.weekOfYear)" + " LIKE %?1% ")
    public Page<Protocol> findAll(String keyword, Pageable pageable);

    List<Protocol> findByUser(User user);

}
