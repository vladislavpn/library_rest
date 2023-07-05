package com.vlad.libraryjparest.service;


import com.vlad.libraryjparest.entity.Client;
import com.vlad.libraryjparest.exception_handling.client_exception.ClientAlreadyExistsException;
import com.vlad.libraryjparest.exception_handling.client_exception.NoSuchClientException;
import com.vlad.libraryjparest.util.ExpirationCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vlad.libraryjparest.repository.ClientRepository;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService{

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private ExpirationCalculator expirationCalculator;

    @Override
    public List<Client> getAllClients(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Client> clients = clientRepository.findAll(pageable).getContent();
        clients.forEach(c -> c.getBooks().forEach(expirationCalculator::calculate));
        return clients;
    }

    @Override
    public Client saveClient(Client client) {
        Boolean clientExists = clientRepository
                .existsClientByFirstNameAndLastNameAndBirthday(client.getFirstName(),
                        client.getLastName(), client.getBirthday());
        if(clientExists) throw new ClientAlreadyExistsException("Such client already exists");
        return clientRepository.save(client);
    }

    @Override
    public Client getClient(int id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchClientException("There is no client with id = " + id));
        client.getBooks().forEach(expirationCalculator::calculate);
        return client;
    }

    @Override
    public void deleteClient(int id) {
        if(!clientRepository.existsById(id))
            throw new NoSuchClientException("There is no client with id = " + id);
        clientRepository.deleteById(id);
    }

    @Override
    public Client updateClient(Client client, int id) {
        if(!clientRepository.existsById(id))
            throw new NoSuchClientException("There is no client with id = " + id);
        return clientRepository.findById(id).map(persisted -> {
            persisted.setFirstName(client.getFirstName());
            persisted.setLastName(client.getLastName());
            persisted.setBirthday(client.getBirthday());
            return clientRepository.save(persisted);
        }).get();
    }

    @Override
    public List<Client> getClientsByFirstName(int pageNo, int pageSize, String name) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Client> clients = clientRepository.findByFirstName(name, pageable);
        if(clients.isEmpty()) throw new NoSuchClientException("There is no client with first name: " + name);
        clients.forEach(c -> c.getBooks().forEach(expirationCalculator::calculate));
        return clients;
    }

    @Override
    public List<Client> getClientsByLastName(int pageNo, int pageSize, String lastName) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Client> clients = clientRepository.findByLastName(lastName, pageable);
        if(clients.isEmpty()) throw new NoSuchClientException("There is no client with last name: " + lastName);
        clients.forEach(c -> c.getBooks().forEach(expirationCalculator::calculate));
        return clients;
    }

    @Override
    public List<Client> getClientsByFullName(int pageNo, int pageSize, String name, String lastName) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Client> clients = clientRepository.findByFirstNameAndLastName(name, lastName, pageable);
        if(clients.isEmpty())
            throw new NoSuchClientException("There is no client with name: " + name + " " + lastName);
        clients.forEach(c -> c.getBooks().forEach(expirationCalculator::calculate));
        return clients;
    }


}
