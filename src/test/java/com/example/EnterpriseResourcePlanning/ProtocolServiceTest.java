package com.example.EnterpriseResourcePlanning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.example.EnterpriseResourcePlanningTESTS.entities.Protocol;
import com.example.EnterpriseResourcePlanningTESTS.entities.User;
import com.example.EnterpriseResourcePlanningTESTS.exceptions.ProtocolNotFoundException;
import com.example.EnterpriseResourcePlanningTESTS.repositories.ProtocolRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.EnterpriseResourcePlanningTESTS.services.ProtocolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.fail;

import org.mockito.InjectMocks;
import org.mockito.Mock;


@ExtendWith(MockitoExtension.class)
public class ProtocolServiceTest {

    @Mock
    private ProtocolRepository protocolRepository;

    @InjectMocks
    private ProtocolService protocolService;

    private Protocol testProtocol;
    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User("TestUser", "testpassword", "testemail@test.com");
        testUser.setId(1L);
        testProtocol = new Protocol("Test Protocol", testUser, "Test Description");
        testProtocol.setId(1L);
    }

    @Test
    public void testListAllWithKeyword() {
        Page<Protocol> expectedPage = mock(Page.class);
        when(protocolRepository.findAll("test", PageRequest.of(0, 5, Sort.by("name").ascending())))
                .thenReturn(expectedPage);

        Page<Protocol> actualPage = protocolService.listAll(1, "name", "asc", "test");

        assertEquals(expectedPage, actualPage);
    }

    @Test
    public void testListAllWithoutKeyword() {
        Page<Protocol> expectedPage = mock(Page.class);
        when(protocolRepository.findAll(PageRequest.of(0, 5, Sort.by("name").ascending())))
                .thenReturn(expectedPage);

        Page<Protocol> actualPage = protocolService.listAll(1, "name", "asc", null);

        assertEquals(expectedPage, actualPage);
    }

    @Test
    public void testSave() {
        protocolService.save(testProtocol);

        Mockito.verify(protocolRepository).save(any(Protocol.class));
    }

    @Test
    public void testGetExistingProtocol() throws ProtocolNotFoundException {
        Optional<Protocol> expectedProtocol = Optional.of(testProtocol);
        when(protocolRepository.findById(1L)).thenReturn(expectedProtocol);

        Protocol actualProtocol = protocolService.get(1L);

        assertEquals(expectedProtocol.get(), actualProtocol);
    }

    @Test
    public void testGetProtocolById() throws ProtocolNotFoundException {

        Protocol protocol = new Protocol();
        when(protocolRepository.findById(1L)).thenReturn(Optional.of(protocol));
        Protocol result = protocolService.get(1L);
        assertEquals(protocol, result);

        when(protocolRepository.findById(2L)).thenReturn(Optional.empty());
        try {
            protocolService.get(2L);
            fail("ProtocolNotFoundException wasn't thrown!!!");
        } catch (ProtocolNotFoundException e) {
            assertEquals("Could not find any Protocols with Id: 2", e.getMessage());
        }
    }

    @Test
    public void testGetNonexistentProtocol() {
        when(protocolRepository.findById(2L)).thenReturn(Optional.empty());

        ProtocolNotFoundException thrown =
                org.junit.jupiter.api.Assertions.assertThrows(
                        ProtocolNotFoundException.class, () -> protocolService.get(2L));

        assertEquals("Could not find any Protocols with Id: 2", thrown.getMessage());
    }

    @Test
    public void testDeleteExistingProtocol() throws ProtocolNotFoundException {
        Long id = 1L;
        Protocol protocol = new Protocol();
        protocol.setId(id);
        when(protocolRepository.countById(id)).thenReturn(1L);
        doNothing().when(protocolRepository).deleteById(id);

        protocolService.delete(id);

        verify(protocolRepository, times(1)).countById(id);
        verify(protocolRepository, times(1)).deleteById(id);
    }


    @Test
    public void testListAllProtocolsByUserWithKeyword() {
        User testUser = new User("TestUser", "testpassword", "testemail@test.com");
        testUser.setId(1L);
        Protocol testProtocol1 = new Protocol("Test Protocol 1", testUser, "Test Description 1");
        testProtocol1.setId(1L);
        Protocol testProtocol2 = new Protocol("Test Protocol 2", testUser, "Test Description 2");
        testProtocol2.setId(2L);

        Page<Protocol> expectedPage = mock(Page.class);
        when(protocolRepository.findAll("test", PageRequest.of(0, 5, Sort.by("name").ascending())))
                .thenReturn(expectedPage);

        Page<Protocol> actualPage = protocolService.listAll( 1, "name", "asc", "test");

        assertEquals(expectedPage, actualPage);
    }



}
