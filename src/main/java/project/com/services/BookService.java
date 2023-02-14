package project.com.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.com.dao.BookDao;
import project.com.models.Book;
import project.com.models.Client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BookService {
  private final   BookDao bookDao;


    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    public Book getBook(int id) {
        return bookDao.getBook(id);
    }


    public void save(Book book) {
        bookDao.save(book);
    }


    public void update(int id, Book updatedBook) {

        bookDao.update(updatedBook,id);
    }


    public void delete(int id) {
        bookDao.delete(id);
    }


    public Client getOwner(int id) {
        return bookDao.bookOwner(id);
    }


    public void changeOwner(Client client, int id) {


        bookDao.changeOwner(client,id);


    }

    public void bookRelease(int id) {

        bookDao.bookRelease(id);

    }


    public List<Book> findAllOrderByYear() {

        return bookDao.findAllOrderByYear();
    }

    ;


    public List<Book> findAllOrderByYearPag(int page, int itemsPerPage) {


        return bookDao.findAllOrderByYearPag(page, itemsPerPage);
    }


    public List<Book> findAllPag(int page, int itemsPerPage) {


        return bookDao.findAllPag(page, itemsPerPage);
    }


    public List<Book> findByNameStartingWith(String query) {
        List<Book> books = bookDao.findByTitleStartingWith(query);
        return books;
    }

    public void isOverdue(List<Book> books) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date1 = "01.11.2022";
        Date temp = null;
        try {
            temp = format.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date = new Date();
        long tenDays = 864000000;
        for (Book book : books) {
            if (book.getTakenAt() == null) {
                book.setOverdue(false);
                continue;
            }
            long diff = date.getTime() - book.getTakenAt().getTime();
            if (diff > tenDays) {
                System.out.println("книжка просрочена");
                book.setOverdue(true);

            } else {
                book.setOverdue(false);
                System.out.println("книжка не просрочена");

            }
        }

    }
}
