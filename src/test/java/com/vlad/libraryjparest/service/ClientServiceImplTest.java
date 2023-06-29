package com.vlad.libraryjparest.service;

import com.vlad.libraryjparest.entity.Book;
import com.vlad.libraryjparest.entity.Client;
import com.vlad.libraryjparest.repository.ClientRepository;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository repository;

    Client client;

    int clientId = 1;

    @InjectMocks
    private ClientServiceImpl service;

    @AfterEach
    void tearDown() {
        client = null;
    }

    @BeforeEach
    void setUp() {
        client = new Client("Test", "Test", new Date());
    }

//    @Test
//    void getAllClients() {
//        service.getAllClients();
//        verify(repository).findAll();
//    }

    @Test
    void saveClient() {
        Client client = new Client();
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

        RuntimeException exception =
                assertThrows(RuntimeException.class, ()-> service.saveClient(client));
        assertEquals("Client already exists", exception.getMessage());
        verify(repository, never()).save(any());
    }


    @Test
    void getClient() {
        given(repository.existsClientById(anyInt())).willReturn(true);
        when(repository.findById(any())).thenReturn(Optional.ofNullable(client));
        Client found = service.getClient(anyInt());
        Assertions.assertNotNull(found);
    }

    @Test
    void deleteClient() {
        when(repository.existsClientById(anyInt())).thenReturn(true);
        service.deleteClient(clientId);
        verify(repository).deleteById(clientId);
    }

    @Test
    void willThrowWhenClientDoesNotExists(){
        when(repository.existsClientById(clientId)).thenReturn(false);
        Exception exception = assertThrows
                (RuntimeException.class, () -> service.deleteClient(clientId));
        assertEquals("Client does not exist", exception.getMessage());
        verify(repository, never()).deleteById(anyInt());
    }
    @Test
    void showBooks() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Test", "Test", 1));
        client.setBooks(books);
        when(repository.findById(clientId)).thenReturn(Optional.ofNullable(client));
        assertTrue(service.showBooks(clientId).equals(books));
    }

//    @Test
//    void getClientsByName() {
//        String name = "test";
//        service.getClientsByName(name);
//        ArgumentCaptor<String> clientArgumentCaptor = ArgumentCaptor.forClass(String.class);
//        verify(repository).findAllByFirstName(clientArgumentCaptor.capture());
//        String capturedName = clientArgumentCaptor.getValue();
//        assertEquals(capturedName, name);
//    }
}