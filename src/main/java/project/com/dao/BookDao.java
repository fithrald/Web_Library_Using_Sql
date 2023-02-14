package project.com.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import project.com.models.Book;
import project.com.models.Client;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDao {
    JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Book> check(String title) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE title LIKE ?", new Object[]{title},
                new BeanPropertyRowMapper<Book>(Book.class)).stream().findAny();
    }

    public List<Book> getBooks() {

        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<Book>(Book.class));
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<Book>(Book.class)).stream().findAny().orElse(null);
    }

    public void update(Book updbook, int id) {
        jdbcTemplate.update("UPDATE Book SET title=? ,author=?,year=? WHERE id=?",
                updbook.getTitle(), updbook.getAuthor(), updbook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public void save(Book Book) {
        jdbcTemplate.update("INSERT  INTO Book(title,author,year)VALUES(?,?,?)", Book.getTitle(), Book.getAuthor(), Book.getYear());
    }

    public Client bookOwner(int id) {
        return jdbcTemplate.query("SELECT *FROM book JOIN Client ON Client.id=client_id WHERE Book.id=?", new Object[]{id},
                new BeanPropertyRowMapper<Client>(Client.class)).stream().findAny().orElse(null);
    }

    public void changeOwner(Client client, int id) {
        jdbcTemplate.update("UPDATE Book SET client_id=? WHERE Book.id=?", client.getId(), id);
        jdbcTemplate.update("UPDATE Book SET takenat=? WHERE id=?", new Date(),id);

    }

    public void bookRelease(int id) {
        jdbcTemplate.update("UPDATE Book SET client_id=NULL WHERE Book.id=?", id);
    }


    public List<Book> findAllOrderByYear() {

        return jdbcTemplate.query("SELECT * FROM Book ORDER BY Book.year", new BeanPropertyRowMapper<Book>(Book.class));

    }


    public List<Book> findAllOrderByYearPag(int page, int itemsPerPage) {


        return jdbcTemplate.query("SELECT * FROM Book ORDER BY YEAR LIMIT ? OFFSET (?- 1)*?",
                new Object[]{itemsPerPage, page, itemsPerPage},
                new BeanPropertyRowMapper<Book>(Book.class));
    }

    public List<Book> findAllPag(int page, int itemsPerPage) {


        return jdbcTemplate.query("SELECT * FROM Book LIMIT ? OFFSET (?- 1)*?",
                new Object[]{itemsPerPage, page, itemsPerPage},
                new BeanPropertyRowMapper<Book>(Book.class));
    }

    public List<Book> findByTitleStartingWith(String query) {
        if (query==null || query.isEmpty() )
            return Collections.emptyList();

        String updQuery = query + "%";
        return jdbcTemplate.query("SELECT * FROM Book where title like ?", new Object[]{updQuery},
                new BeanPropertyRowMapper<Book>(Book.class));
    }
}




