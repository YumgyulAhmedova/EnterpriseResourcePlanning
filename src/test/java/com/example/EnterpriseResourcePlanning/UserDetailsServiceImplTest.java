package com.example.EnterpriseResourcePlanning;

import com.example.EnterpriseResourcePlanningTESTS.Authorisation.MyUserDetails;
import com.example.EnterpriseResourcePlanningTESTS.entities.User;
import com.example.EnterpriseResourcePlanningTESTS.exceptions.UserNotFoundException;
import com.example.EnterpriseResourcePlanningTESTS.repositories.UserRepository;
import com.example.EnterpriseResourcePlanningTESTS.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testLoadUserByUsername() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("testpassword");

        when(userRepository.getUserByUsername(anyString())).thenReturn(user);

        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername("testuser");

        assertEquals("testuser", userDetails.getUsername());
        assertEquals("testpassword", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameThrowsException() {
        when(userRepository.getUserByUsername(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonexistentuser"));
    }

    @Test
    void testIsUserExist() {
        User user = new User();
        user.setUsername("testuser");

        when(userRepository.getUserByUsername(anyString())).thenReturn(user);

        assertTrue(userDetailsService.isUserExist(user));
    }

    @Test
    void testIsUserExistReturnsFalse() {
        when(userRepository.getUserByUsername(anyString())).thenReturn(null);

        User user = new User();
        user.setUsername("testuser");

        assertFalse(userDetailsService.isUserExist(user));
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userDetailsService.saveUser(user);

        verify(bCryptPasswordEncoder, times(1)).encode("testpassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testListAll() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("testuser1");
        user1.setPassword("testpassword1");
        users.add(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("testuser2");
        user2.setPassword("testpassword2");
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        Iterable<User> result = userDetailsService.listAll();

        assertNotNull(result);
        assertEquals(2, ((List<User>) result).size());
    }

    @Test
    public void testIsUsernameUnique() {

        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.getUserByUsername(username)).thenReturn(user);
        boolean result = userDetailsService.isUsernameUnique(username,user.getId());
        assertFalse(result);
    }

    @Test
    void loadUserByUsername_whenUserExists_shouldReturnUserDetails() {
        User user = new User("testuser", "password");
        user.setId(1L);
        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(user);

        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(user.getUsername());

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_whenUserDoesNotExist_shouldThrowException() {
        String username = "testuser";
        when(userRepository.getUserByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }

    @Test
    public void testDeleteUser() throws UserNotFoundException {
        Long id = 1L;
        User user = new User();
        user.setId(id);

        when(userRepository.countById(id)).thenReturn(1L);
        userDetailsService.delete(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteUserNotFound() {
        Long id = 1L;

        when(userRepository.countById(id)).thenReturn(0L);

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userDetailsService.delete(id);
        });
    }

    @Test
    public void testGetUser() throws UserNotFoundException {
        Long id = 1L;
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userDetailsService.get(id);

        Assertions.assertEquals(user, result);
    }

    @Test
    public void testGetUserNotFound() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userDetailsService.get(id);
        });
    }

    @Test
    public void testListAllUsers() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = (List<User>) userDetailsService.listAll();

        Assertions.assertEquals(userList, result);
    }

    @Test
    public void testListAllUsersWithKeyword() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll("user")).thenReturn(userList);

        List<User> result = userDetailsService.listAll("user");

        Assertions.assertEquals(userList, result);
    }


    @Test
    public void testGetAllUsers() {

        User user1 = mock(User.class);
        User user2 = mock(User.class);
        User user3 = mock(User.class);

        List<User> allUsers = Arrays.asList(user1, user2, user3);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
        List<User> users = (List<User>) userDetailsService.listAll();

        assertEquals(3, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user3));
    }

//    @Test
//    public void testGetCurrentUser() {
//        User user = new User();
//        user.setUsername("testuser");
//
//        when(userRepository.getUserByUsername("testuser")).thenReturn(user);
//
//        User result = userDetailsService.getCurrentUser();
//
//        Assertions.assertEquals(user, result);
//    }


    @Test
    void testDeleteUserNotFoundException() {
        Long id = 1L;
        Long nullCount = null;
        doReturn(nullCount).when(userRepository).countById(id);

        assertThrows(UserNotFoundException.class, () -> userDetailsService.delete(id));
        verify(userRepository, times(1)).countById(id);
        verify(userRepository, never()).deleteById(id);
    }



}