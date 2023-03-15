package com.example.EnterpriseResourcePlanning;

import com.example.EnterpriseResourcePlanningTESTS.entities.User;
import com.example.EnterpriseResourcePlanningTESTS.enums.Role;
import com.example.EnterpriseResourcePlanningTESTS.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@ExtendWith(MockitoExtension.class)
public class UserRepositoryTests {

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setEmail("testuser@example.com");
        user.setMobile("1234567890");
        user.setRole(Role.USER);
        user.setCreatedAt(new Date());
    }

    @Test
    void getUserByUsername_validUsername_returnsUser() {

        String username = "testuser";
        User expectedUser = new User();
        expectedUser.setUsername(username);
        when(userRepository.getUserByUsername(username)).thenReturn(expectedUser);

        User resultUser = userRepository.getUserByUsername(username);

        assertNotNull(resultUser);
        assertEquals(expectedUser.getUsername(), resultUser.getUsername());
    }

    @Test
    void findAll_validKeyword_returnsListOfUsers() {
        String keyword = "test";
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User());
        when(userRepository.findAll("%" + keyword + "%")).thenReturn(expectedUsers);

        List<User> resultUsers = userRepository.findAll("%" + keyword + "%");

        assertNotNull(resultUsers);
        assertEquals(expectedUsers.size(), resultUsers.size());
    }

    @Test
    void countById_validId_returnsCount() {
        Long id = 1L;
        Long expectedCount = 1L;
        when(userRepository.countById(id)).thenReturn(expectedCount);

        Long resultCount = userRepository.countById(id);

        assertNotNull(resultCount);
        assertEquals(expectedCount, resultCount);
    }

}

