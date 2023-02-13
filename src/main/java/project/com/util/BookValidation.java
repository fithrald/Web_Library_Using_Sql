package project.com.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.com.dao.BookDao;
import project.com.models.Book;
@Component
public class BookValidation implements Validator {
    BookDao bookDao;

    public BookValidation(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Book.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book= (Book) o;
       if( bookDao.check(book.getTitle()).isPresent())
        errors.rejectValue("title","","that title is already exist");


    }
}
