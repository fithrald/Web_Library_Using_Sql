package project.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.com.dao.BookDao;
import project.com.dao.ClientDao;
import project.com.models.Book;
import project.com.models.Client;

import java.util.List;


@Service

public class ClientService {

    private final BookDao bookDao;
    private final ClientDao clientDao;

    @Autowired
    public ClientService(BookDao bookDao, ClientDao clientDao) {
        this.bookDao = bookDao;
        this.clientDao = clientDao;
    }

    public List<Client> getClients() {
        return clientDao.getClients();
    }

    public Client getClient(int id) {
        return clientDao.getClient(id);
    }

    public void save(Client client) {
        clientDao.save(client);
    }

    public void update(int id, Client updClient) {

        clientDao.update(updClient, id);
    }

    public void delete(int id) {
        clientDao.delete(id);

    }

    public List<Book> checkBooks(int id) {
        return clientDao.checkBooks(id);
    }
}
