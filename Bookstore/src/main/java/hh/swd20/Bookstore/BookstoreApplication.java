package hh.swd20.Bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import hh.swd20.Bookstore.domain.Book;
import hh.swd20.Bookstore.domain.BookRepository;
import hh.swd20.Bookstore.domain.Category;
import hh.swd20.Bookstore.domain.CategoryRepository;
import hh.swd20.Bookstore.domain.User;
import hh.swd20.Bookstore.domain.UserRepository;

@SpringBootApplication
public class BookstoreApplication {
	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner bookDemo(BookRepository brepository, CategoryRepository drepository, UserRepository urepository) {
		return (args) -> {
			log.info("save a couple of books");
			drepository.save(new Category("IT"));
			drepository.save(new Category("Business"));
			drepository.save(new Category("Law"));
			drepository.save(new Category("Lifestory"));
			
			brepository.save(new Book("Ehsan Haidari", "Ehsanin kirja", "1979", "123456-789", drepository.findByName("Lifestory").get(0)));
			brepository.save(new Book("Matti Meikäläinen", "Matin kirja", "1961", "123456-789", drepository.findByName("Law").get(0)));	
			
			// Create users: admin/admin user/user
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			User user2 = new User("admin", "$2a$04$qoE8BRZ8N2T1QzJH69ZMLej9608mR7okHVWKQEJ6.V9BzRdlgr2.e", "ADMIN");
			urepository.save(user1);
			urepository.save(user2);
			
			log.info("fetch all books");
			for (Book book : brepository.findAll()) {
				log.info(book.toString());
			}
		};
	}
}
