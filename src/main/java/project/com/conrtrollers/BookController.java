package project.com.conrtrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.com.models.Book;
import project.com.models.Client;
import project.com.services.BookService;
import project.com.services.ClientService;
import project.com.util.BookValidation;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/book")
public class BookController {

    BookValidation bookValidation;
    BookService bookService;
    ClientService clientService;

    @Autowired
    public BookController(
            BookValidation bookValidation,
            BookService bookService,
            ClientService clientService) {


        this.bookValidation = bookValidation;
        this.bookService = bookService;
        this.clientService = clientService;
    }

    @GetMapping("/info")
    public String booksInfo(Model model,
                            @RequestParam(value = "sort_by_year", required = false) boolean sort_by_year,
                            @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "books_per_page", required = false) Integer books_per_page) {
        if (!sort_by_year)
            model.addAttribute("books", bookService.getBooks());
        else
            model.addAttribute("books", bookService.findAllOrderByYear());


        if (page != null && books_per_page != null)

            model.addAttribute("books", bookService.findAllOrderByYearPag(page, books_per_page));

        if (page != null && books_per_page != null && !sort_by_year)
            model.addAttribute("books", bookService.findAllPag(page, books_per_page));

        return "books/books";
    }


    @GetMapping("/{id}")
    public String bookInfo(@PathVariable("id") int id, Model model, @ModelAttribute Client client) {
        model.addAttribute("book", bookService.getBook(id));

        Optional<Client> owner = Optional.ofNullable(bookService.getOwner(id));

        if (owner.isPresent())
            model.addAttribute("owner", owner.get());

        model.addAttribute("clients", clientService.getClients());
        return "books/book";
    }

    @GetMapping("/edit/{id}")
    public String bookEdit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.getBook(id));
        return "books/bookEdit";
    }

    @PatchMapping("/{id}")
    public String bookPatch(@PathVariable("id") int id, @ModelAttribute @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "books/bookEdit";
        bookService.update(id, book);
        return "redirect:/book/info";
    }

    @DeleteMapping("/{id}")
    public String bookDelete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/book/info";
    }

    @GetMapping("/new")
    public String bookNew(@ModelAttribute Book book) {
        return "books/newBook";
    }

    @PostMapping("/new")
    public String producing(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        bookValidation.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/newBook";
        bookService.save(book);
        return "redirect:/book/info";
    }

    @PostMapping("/assign/{id}")
    public String bookAssign(@ModelAttribute Client client, @PathVariable("id") int id, Model model) {


        bookService.changeOwner(client, id);
        return "redirect:/book/" + id;
    }

    @DeleteMapping("/release/{id}")
    public String bookRelease(@PathVariable("id") int id, Model model) {
        bookService.bookRelease(id);
        return "redirect:/book/" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/searchBook";
    }

    @PostMapping("/search")
    public String makeSearch(
            @RequestParam(value = "query", required = false) String query,
            Model model) {
        List<Book> books = bookService.findByNameStartingWith(query);

        books.forEach(book -> book.setOwner(bookService.getOwner(book.getId())));
        model.addAttribute("books", books);
        return "books/searchBook";
    }
}
