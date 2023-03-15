package com.example.EnterpriseResourcePlanningTESTS.repositories;

import com.example.EnterpriseResourcePlanningTESTS.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    public User getUserByUsername(@Param("username") String username);

    @Query("SELECT c FROM User c WHERE " + "CONCAT(c.id, c.username, c.email)" + "LIKE %?1%")
    public List<User> findAll(String keyword);

    Long countById(Long id);

//
//    User findByUsername(String username);
}
