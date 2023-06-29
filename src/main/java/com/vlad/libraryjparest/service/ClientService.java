package com.vlad.libraryjparest.service;



import com.vlad.libraryjparest.entity.Book;
import com.vlad.libraryjparest.entity.Client;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ClientService {
    public List<Client> getAllClients(int pageNo, int pageSize);
    public void saveClient(Client client);
    public Client getClient(int id);
    public void deleteClient(int id);
    public List<Client> getClientsByFirstName(int pageNo, int pageSize, String name);
    public List<Client> getClientsByLastName(int pageNo, int pageSize, String lastName);
    public List<Client> getClientsByFullName(int pageNo, int pageSize, String name, String lastName);
}
