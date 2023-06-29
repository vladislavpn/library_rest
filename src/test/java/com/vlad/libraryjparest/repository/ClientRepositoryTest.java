package com.vlad.libraryjparest.repository;
;
import com.vlad.libraryjparest.entity.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @AfterEach
    void tearDown(){
        clientRepository.deleteAll();
    }

    @Test
    void canFindByFirstName() {
        String name = "Test";
        Client client1 = new Client("Test", "Entity", new Date());
        Client client2 = new Client("Test", "Entity2", new Date());
        clientRepository.save(client1);
        clientRepository.save(client2);
        List<Client> clients = clientRepository.findAllByFirstName(name);
        Boolean exists = clients.stream().
                map(o -> o.getFirstName()).
                allMatch(o -> o.equals(name)) && clients.size()==2;
        assertTrue(exists);
    }
}