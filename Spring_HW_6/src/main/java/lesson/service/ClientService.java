package lesson.service;

import lesson.domain.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAll();

    Client getById(Long id);

    Client save(Client client);
}
