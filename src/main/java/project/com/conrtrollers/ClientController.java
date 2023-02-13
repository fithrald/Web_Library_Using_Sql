package project.com.conrtrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.com.dao.ClientDao;
import project.com.models.Book;
import project.com.models.Client;
import project.com.services.BookService;
import project.com.services.ClientService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {
    ClientService clientService;
    BookService bookService;

    @Autowired
    public ClientController(ClientService clientService, BookService bookService, ClientDao clientDao) {
        this.clientService = clientService;
        this.bookService = bookService;

    }
    @GetMapping("/info")
    public String clientsInfo(Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "clients/clients";
    }
    @GetMapping("/{id}")
    public String clientInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("client", clientService.getClient(id));
        List<Book> books = clientService.checkBooks(id);
        bookService.isOverdue(books);
        model.addAttribute("books", books);
        return "clients/client";
    }



    @GetMapping("/edit/{id}")
    public String clientEdit(@PathVariable("id") int id, Model model) {
        model.addAttribute("client", clientService.getClient(id));
        return "clients/clientEdit";
    }

    @PatchMapping("/{id}")
    public String clientPatch(@PathVariable("id") int id, @ModelAttribute @Valid Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "clients/clientEdit";
        clientService.update(id, client);
        return "redirect:/client/info";
    }

    @DeleteMapping("/{id}")
    public String clientDelete(@PathVariable("id") int id) {
        clientService.delete(id);
        return "redirect:/client/info";
    }

    @GetMapping("/new")
    public String clientNew(@ModelAttribute Client client) {
        return "clients/newClient";
    }

    @PostMapping("/new")
    public String producing(@ModelAttribute @Valid Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "clients/newClient";
        clientService.save(client);
        return "redirect:/client/info";
    }

}


