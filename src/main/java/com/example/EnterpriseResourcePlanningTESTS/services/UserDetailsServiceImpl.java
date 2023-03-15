package com.example.EnterpriseResourcePlanningTESTS.services;

import com.example.EnterpriseResourcePlanningTESTS.entities.User;
import com.example.EnterpriseResourcePlanningTESTS.exceptions.UserNotFoundException;
import com.example.EnterpriseResourcePlanningTESTS.repositories.UserRepository;
import com.example.EnterpriseResourcePlanningTESTS.Authorisation.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }

    public Boolean isUserExist(User user) {
        User userByUsername = userRepository.getUserByUsername(user.getUsername());
        return (userByUsername != null);
    }

    public void saveUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public Iterable<User> listAll() {
        return userRepository.findAll();
    }

    public List<User> listAll(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return userRepository.findAll(keyword);
        }
        return (List<User>) userRepository.findAll();
    }


//    public List<User> listAll(String keyword) {
//        if (keyword != null && !keyword.isEmpty()) {
//            return userRepository.findByUsernameContainingIgnoreCase(keyword);
//        }
//        return (List<User>) userRepository.findAll();
//    }

    public User get(Long id) throws UserNotFoundException {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find any Users with Id: " + id);
    }

    public void delete(Long id) throws UserNotFoundException {
        Long count = userRepository.countById(id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any Users with Id: " + id);
        }
        userRepository.deleteById(id);
    }


    public void addAllUsers(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
    }


    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.getUserByUsername(username);
    }

//    public User getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        return userRepository.findByUsername(username);
//    }


    public boolean isUsernameUnique(String username, Long id) {
        User userByUsername = userRepository.getUserByUsername(username);

        if (userByUsername == null) {
            return true;
        }

        if (id != null && userByUsername.getId().equals(id)) {
            return true;
        }

        return false;
    }

}
