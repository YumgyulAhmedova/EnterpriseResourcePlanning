package com.example.EnterpriseResourcePlanningTESTS.services;

import com.example.EnterpriseResourcePlanningTESTS.entities.Protocol;
import com.example.EnterpriseResourcePlanningTESTS.entities.User;
import com.example.EnterpriseResourcePlanningTESTS.exceptions.ProtocolNotFoundException;
import com.example.EnterpriseResourcePlanningTESTS.repositories.UserRepository;
import com.example.EnterpriseResourcePlanningTESTS.repositories.ProtocolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProtocolService {

    @Autowired
    private ProtocolRepository protocolRepository;

    @Autowired
    private UserRepository userRepository;


    public Page<Protocol> listAll(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1,5, sort);

        if(keyword != null){
            return protocolRepository.findAll(keyword, pageable);
        }

        return protocolRepository.findAll(pageable);
    }


    public void save(Protocol protocol) {
        protocolRepository.save(protocol);
    }

    public Protocol get(Long id) throws ProtocolNotFoundException {
        Optional<Protocol> result = protocolRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ProtocolNotFoundException("Could not find any Protocols with Id: " + id);
    }

    public void delete(Long id) throws ProtocolNotFoundException {
        Long count = protocolRepository.countById(id);
        if (count == null || count == 0) {
            throw new ProtocolNotFoundException("Could not find any Protocols with Id: " + id);
        }
        protocolRepository.deleteById(id);
    }


//    public List<Protocol> findProtocol(String name){  /*/*/
//        return protocolRepository.findByName(name);
//    }
//
//    public List<Protocol> searchProtocol(String name){  /*/*/
//        return protocolRepository.searchByPartialName(name);
//    }


    @Autowired
    private UserDetailsServiceImpl userService;

//
//    public Iterable<Protocol> findAllByCurrentUser() {
//        User currentUser = userService.getCurrentUser();
//        if (currentUser.isAdmin()) {
//            return protocolRepository.findAll();
//        } else {
//            return protocolRepository.findByUser(currentUser);
//        }
//    }




}
