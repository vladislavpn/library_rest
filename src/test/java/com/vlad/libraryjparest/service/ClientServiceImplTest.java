package com.vlad.libraryjparest.service;

import com.vlad.libraryjparest.entity.Book;
import com.vlad.libraryjparest.entity.Client;
import com.vlad.libraryjparest.exception_handling.client_exception.ClientAlreadyExistsException;
import com.vlad.libraryjparest.exception_handling.client_exception.NoSuchClientException;
import com.vlad.libraryjparest.repository.ClientRepository;
import com.vlad.libraryjparest.util.ExpirationByDays;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository repository;

    private Client client;

    private int clientId = 1;

    private int pageNo = 1;
    private int pageSize = 3;


    @InjectMocks
    private ClientServiceImpl service;

    @AfterEach
    void tearDown() {
        client = null;
    }

    @BeforeEach
    void setUp() {
        client = new Client("Test", "Test", new Date());
        client.setBooks(new ArrayList<>());
    }

    @Test
    void getAllClients() {
        when(repository.findAll(any(Pageable.class))).thenReturn(Page.empty());
        service.getAllClients(pageNo, pageSize);
        verify(repository).findAll(Pageable.ofSize(pageSize).withPage(pageNo));
    }

    @Test
    void saveClient() {
        service.saveClient(client);
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(repository).save(clientArgumentCaptor.capture());
        Client capturedClient = clientArgumentCaptor.getValue();
        assertEquals(capturedClient, client);
    }

    @Test
    void willThrowWhenClientExists() {
        when(repository.existsClientByFirstNameAndLastNameAndBirthday(client.getFirstName(),
                client.getLastName(), client.getBirthday())).thenReturn(true);
        ClientAlreadyExistsException exception =
                assertThrows(ClientAlreadyExistsException.class, ()-> service.saveClient(client));
        assertEquals("Such client already exists", exception.getMessage());
        verify(repository, never()).save(any());
    }


    @Test
    void getClient() {
        when(repository.findById(anyInt())).thenReturn(Optional.ofNullable(client));
        Client found = service.getClient(anyInt());
        Assertions.assertNotNull(found);
    }

    @Test
    void willThrowWhenClientDoesNotExists(){
        String testName = "Test";

        when(repository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        when(repository.existsById(anyInt())).thenReturn(false);
        when(repository.findByFirstName(any(), any())).thenReturn(List.of());
        when(repository.findByLastName(any(), any())).thenReturn(List.of());
        when(repository.findByFirstNameAndLastName(any(), any(), any())).thenReturn(List.of());

        NoSuchClientException exceptionWhenGet = assertThrows
                (NoSuchClientException.class, () -> service.getClient(clientId));
        assertEquals("There is no client with id = " + clientId, exceptionWhenGet.getMessage());

        NoSuchClientException exceptionWhenDelete = assertThrows
                (NoSuchClientException.class, () -> service.deleteClient(clientId));
        assertEquals("There is no client with id = " + clientId, exceptionWhenDelete.getMessage());
        verify(repository, never()).deleteById(anyInt());

        NoSuchClientException exceptionWhenNoFirstName = assertThrows
                (NoSuchClientException.class, () -> service.getClientsByFirstName(pageNo, pageSize, testName));
        assertEquals("There is no client with first name: " + testName, exceptionWhenNoFirstName.getMessage());

        NoSuchClientException exceptionWhenNoLastName = assertThrows
                (NoSuchClientException.class, () -> service.getClientsByLastName(pageNo, pageSize, testName));
        assertEquals("There is no client with last name: " + testName, exceptionWhenNoLastName.getMessage());

        NoSuchClientException exceptionWhenNoFirstAndLastName = assertThrows
                (NoSuchClientException.class, () -> service.getClientsByFullName(pageNo, pageSize, testName, testName));
        assertEquals("There is no client with name: " + testName + " " + testName, exceptionWhenNoFirstAndLastName.getMessage());

        NoSuchClientException exceptionWhenUpdate = assertThrows
                (NoSuchClientException.class, () -> service.updateClient(client, clientId));
        assertEquals("There is no client with id = " + clientId, exceptionWhenUpdate.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void deleteClient() {
        when(repository.existsById(anyInt())).thenReturn(true);
        service.deleteClient(clientId);
        verify(repository).deleteById(clientId);
    }

    @Test
    void getClientsByFirstName() {
        String name = "test";
        when(repository.findByFirstName(any(), any())).thenReturn(List.of(client));
        service.getClientsByFirstName(pageNo, pageSize, name);
        ArgumentCaptor<String> nameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository).findByFirstName(nameArgumentCaptor.capture(), any(Pageable.class));
        String capturedName = nameArgumentCaptor.getValue();
        assertEquals(capturedName, name);
    }

    @Test
    void getClientsByLastName() {
        String name = "test";
        when(repository.findByLastName(any(), any())).thenReturn(List.of(client));
        service.getClientsByLastName(pageNo, pageSize, name);
        ArgumentCaptor<String> nameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository).findByLastName(nameArgumentCaptor.capture(), any(Pageable.class));
        String capturedName = nameArgumentCaptor.getValue();
        assertEquals(capturedName, name);
    }

    @Test
    void getClientsByFullName() {
        String firstName = "first";
        String lastName = "last";
        when(repository.findByFirstNameAndLastName(any(), any(), any())).thenReturn(List.of(client));
        service.getClientsByFullName(pageNo, pageSize, firstName, lastName);
        ArgumentCaptor<String> firstNameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> lastNameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository).findByFirstNameAndLastName(firstNameArgumentCaptor.capture(),
                lastNameArgumentCaptor.capture(), any(Pageable.class));
        String capturedName = firstNameArgumentCaptor.getValue();
        String capturedLastName = lastNameArgumentCaptor.getValue();
        assertEquals(capturedName, firstName);
        assertEquals(capturedLastName, lastName);
    }

    @Test
    void updateClient(){
        when(repository.existsById(any())).thenReturn(true);
        when(repository.findById(any())).thenReturn(Optional.of(new Client()));
        when(repository.save(any())).thenReturn(client);
        service.updateClient(client, anyInt());
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(repository).save(clientArgumentCaptor.capture());
        Client captured = clientArgumentCaptor.getValue();
        assertEquals(captured.getFirstName(), client.getFirstName());
        assertEquals(captured.getLastName(), client.getLastName());
        assertEquals(captured.getBirthday(), client.getBirthday());
    }
}