package com.example.EnterpriseResourcePlanningTESTS.repositories;

import com.example.EnterpriseResourcePlanningTESTS.entities.Protocol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SearchRepository extends PagingAndSortingRepository<Protocol, Long> {

    @Query("SELECT p FROM Protocol p WHERE p.user.username LIKE %:keyword% OR p.weekOfYear = :weekNumber")
    public Page<Protocol> findAll(@Param("keyword") String keyword, @Param("weekNumber") int weekNumber, Pageable pageable);

}
