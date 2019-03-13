package hh.swd20.Bookstore.web;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.Bookstore.domain.Book;
import hh.swd20.Bookstore.domain.BookRepository;
import hh.swd20.Bookstore.domain.CategoryRepository;



@Controller
public class BookController {
	@Autowired
	private BookRepository brepository;
	
	@Autowired
	private CategoryRepository drepository; 
	
	//näytä kaikki kirjat, ekana sisäänkirjautumisella
    @RequestMapping(value="/login")
    public String login() {	
        return "login";
    }
	// RESTful service to get all books
    @RequestMapping(value="/books", method = RequestMethod.GET)
    public @ResponseBody List<Book> bookListRest() {
        return (List<Book>) brepository.findAll();
    }    

	// RESTful service to get book by id
    @RequestMapping(value="/book/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Book> findBookRest(@PathVariable("id") Long bookId) {	
    	return brepository.findById(bookId);
    }  
    // RESTful service to add new book to db
    @RequestMapping(value= "/books", method=RequestMethod.POST)
    public @ResponseBody Book addNewBookRest(@RequestBody Book book) {
    	return brepository.save(book);
    }
    
	@RequestMapping(value="/booklist")
	public String newBook(Model model) {
		model.addAttribute("books", brepository.findAll());
		return "booklist";
	}
   
    @RequestMapping(value = "/add")
    public String addStudent(Model model){
    	model.addAttribute("book", new Book());
    	model.addAttribute("departments", drepository.findAll());
        return "addbook";
    }  
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Book book){
        brepository.save(book);
        return "redirect:booklist";
    } 
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
    	brepository.deleteById(bookId);
        return "redirect:../booklist";
    } 
    
    @RequestMapping(value = "/edit/{id}")
    public String editBook(@PathVariable("id") Long bookId, @PathVariable("id") Long categoryId, Model model) {
    	model.addAttribute("book", brepository.findById(bookId));
    	model.addAttribute("categorys", drepository.findAll());
        return "editbook";
	
}
}

