package lesson.service;


import lesson.domain.Product;
import lesson.repository.ProductDAO;
import org.springframework.stereotype.Service;
import lesson.domain.Client;
import lesson.repository.ClientDAO;

import java.util.Arrays;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDAO clientDAO;

    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
        init();
    }

    private void init(){

        clientDAO.saveAll(Arrays.asList(
                new Client(null, "Konstantin", "abc@mail.ru", "+79118655943"),
                new Client(null, "Ivan", "dfg@mail.ru", "+79111754547")
        ));

    }

    @Override
    public List<Client> getAll() {
        return clientDAO.findAll();
    }

    @Override
    public Client getById(Long id) {
        return clientDAO.findById(id).orElse(null);
    }

    @Override
    public Client save(Client client) {
        return clientDAO.save(client);
    }
}
