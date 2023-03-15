package com.example.EnterpriseResourcePlanningTESTS.services;

import com.example.EnterpriseResourcePlanningTESTS.entities.Protocol;
import com.example.EnterpriseResourcePlanningTESTS.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchRepository searchRepository;


    public Page<Protocol> listAll(int pageNumber, String sortField, String sortDir, String keyword, int weekNumber) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1,10, sort);

        if(keyword != null){
            return searchRepository.findAll(keyword, weekNumber, pageable);
        }

        return searchRepository.findAll(pageable);
    }


    public int calculateTotalHours(List<Protocol> listProtocols) {
        int totalHours = 0;

        for (Protocol protocol : listProtocols) {
            int protocolHours = protocol.getHours();
            protocol.setTotalHours(protocolHours);
            totalHours += protocolHours;
        }

        return totalHours;
    }


}
