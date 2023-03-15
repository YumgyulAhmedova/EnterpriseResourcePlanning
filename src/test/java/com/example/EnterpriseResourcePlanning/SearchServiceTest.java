package com.example.EnterpriseResourcePlanning;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.EnterpriseResourcePlanningTESTS.entities.Protocol;
import com.example.EnterpriseResourcePlanningTESTS.entities.User;
import com.example.EnterpriseResourcePlanningTESTS.repositories.SearchRepository;
import com.example.EnterpriseResourcePlanningTESTS.services.SearchService;

public class SearchServiceTest {

    @Mock
    private SearchRepository searchRepository;

    @InjectMocks
    private SearchService searchService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListAll() {
        User user = new User("John Doe", "john.doe@example.com", "password");
        List<Protocol> protocols = new ArrayList<>();
        protocols.add(new Protocol(1L, "Activity 1", 4, user));
        protocols.add(new Protocol(2L, "Activity 2", 3, user));
        Page<Protocol> page = new PageImpl<>(protocols);

        when(searchRepository.findAll(PageRequest.of(0, 10, Sort.by("name"))))
                .thenReturn(page);

        Page<Protocol> result = searchService.listAll(1, "name", "asc", null, 0);

        assertEquals(protocols.size(), result.getContent().size());
        assertEquals(protocols.get(0).getName(), result.getContent().get(0).getName());
        assertEquals(protocols.get(1).getName(), result.getContent().get(1).getName());
    }

    @Test
    public void testCalculateTotalHours() {
        List<Protocol> protocols = new ArrayList<>();
        protocols.add(new Protocol(1L, "Activity 1", 4, null));
        protocols.add(new Protocol(2L, "Activity 2", 3, null));

        int result = searchService.calculateTotalHours(protocols);

        assertEquals(7, result);
    }

}

