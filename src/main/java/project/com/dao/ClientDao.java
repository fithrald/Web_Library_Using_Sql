package project.com.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import project.com.models.Book;
import project.com.models.Client;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


@Repository
public class ClientDao {
    JdbcTemplate jdbcTemplate;

    public ClientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> checkBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE client_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<Book>(Book.class));


    }

    public List<Client> getClients() {

        return jdbcTemplate.query("SELECT * FROM Client", new BeanPropertyRowMapper<Client>(Client.class));
    }

    public Client getClient(int id) {
        return jdbcTemplate.query("SELECT * FROM Client WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<Client>(Client.class)).stream().findAny().orElse(null);
    }

    public void update(Client updClient, int id) {
        jdbcTemplate.update("UPDATE Client SET FIO=? ,birth_year=? WHERE id=?", updClient.getFIO(), updClient.getBirth_year(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Client WHERE id=?", id);

    }

    public void save(Client client) {
        jdbcTemplate.update("INSERT  INTO Client(FIO,birth_year)VALUES(?,?)", client.getFIO(), client.getBirth_year());
    }


}
