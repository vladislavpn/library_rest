package com.vlad.libraryjparest.controller;


import com.vlad.libraryjparest.entity.Client;
import com.vlad.libraryjparest.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<Client> allClients(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String lastName,
                                   @RequestParam(required = false, defaultValue = "0") int pageNo,
                                   @RequestParam(required = false, defaultValue = "10") int pageSize){
        List<Client> clients;
        if(name != null && lastName != null) clients = clientService.getClientsByFullName(pageNo, pageSize,
                name, lastName);
        else if(name != null) clients = clientService.getClientsByFirstName(pageNo, pageSize, name);
        else if(lastName != null) clients = clientService.getClientsByLastName(pageNo, pageSize, lastName);
        else clients = clientService.getAllClients(pageNo, pageSize);
        return clients;
    }

    @GetMapping("/clients/{id}")
    public Client clientInfo(@PathVariable int id){
        Client client = clientService.getClient(id);
        return client;
    }


    @PostMapping ("/clients")
    public Client addNewClient(@RequestBody Client client){
        clientService.saveClient(client);
        return client;
    }

    @DeleteMapping("/clients/{id}")
    public String deleteClient(@PathVariable int id){
        clientService.deleteClient(id);
        return "Client with id = " + id + " was deleted";
    }

    @PatchMapping("/clients/{id}")
    public Client updateClient(@RequestBody Client client, @PathVariable int id){
        return clientService.updateClient(client, id);
    }

}
