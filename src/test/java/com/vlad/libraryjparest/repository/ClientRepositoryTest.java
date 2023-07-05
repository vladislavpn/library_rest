package com.vlad.libraryjparest.repository;
;
import com.vlad.libraryjparest.entity.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Client client1;
    private Client client2;

    @BeforeEach
    void setUp() {
        client1 = new Client("Test", "Entity", new Date());
        client2 = new Client("Test", "Entity", new Date());
        clientRepository.save(client1);
        clientRepository.save(client2);
    }

    @AfterEach
    void tearDown(){
        clientRepository.deleteAll();
    }

    @Test
    void canFindByFirstName() {
        String name = "Test";
        List<Client> clients = clientRepository.findByFirstName(name, any());
        assertTrue(clients.stream().map(o -> o.getFirstName()).
                allMatch(o -> o.equals(name)));
        assertTrue(clients.size()==2);
    }

    @Test
    void canFindByLastName() {
        String lastName = "Entity";
        List<Client> clients = clientRepository.findByLastName(lastName, any());
        assertTrue(clients.stream().map(o -> o.getLastName()).
                allMatch(o -> o.equals(lastName)));
        assertTrue(clients.size()==2);
    }

    @Test
    void canFindByFirstNameAndLastName() {
        String testName = "Test";
        String entityLastName = "Entity";
        List<Client> clients = clientRepository.findByFirstNameAndLastName(testName, entityLastName, any());
        assertTrue(clients.stream().map(o -> o.getLastName()).allMatch(o -> o.equals(entityLastName)));
        assertTrue(clients.stream().map(o -> o.getFirstName()).allMatch(o -> o.equals(testName)));
        assertTrue(clients.size()==2);
    }

    @Test
    void canReturnExistsClientByFirstNameAndLastNameAndBirthday() {
        assertTrue(clientRepository
                .existsClientByFirstNameAndLastNameAndBirthday(client1.getFirstName(),
                        client1.getLastName(), client1.getBirthday()));
        assertFalse(clientRepository
                .existsClientByFirstNameAndLastNameAndBirthday("Not exists",
                        client1.getLastName(), client1.getBirthday()));
        assertFalse(clientRepository
                .existsClientByFirstNameAndLastNameAndBirthday(client1.getFirstName(),
                        "Not exists", client1.getBirthday()));
        assertFalse(clientRepository.existsClientByFirstNameAndLastNameAndBirthday(client1.getFirstName(),
                client1.getLastName(), new Date(2)));
    }

}