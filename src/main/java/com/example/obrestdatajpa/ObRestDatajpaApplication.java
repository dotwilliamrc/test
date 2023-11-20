package com.example.obrestdatajpa;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ObRestDatajpaApplication {

	public static void main(String[] args) {
		 ApplicationContext context = SpringApplication.run(ObRestDatajpaApplication.class, args);
         BookRepository repository = context.getBean(BookRepository.class);
         
         // Crud
         // crear libro
         Book book1 = new Book(null, "Homo Deus", "Yuval Noah", 450, 29.99, LocalDate.of(2018, Month.DECEMBER, 1), true);
         Book book2 = new Book(null, "Homo Sapiens", "Yuval Noah", 450, 19.99, LocalDate.of(2013, 12, 1), true);
         
         // almacentar un libro
         repository.save(book1);
         repository.save(book2);

         // recuperar todos los libros
         List<Book> Books = repository.findAll();
         Books.forEach((book) -> {
                 System.out.println(book);
             });
         

         System.out.println();
         // borrar un libro
         // repository.delete(book1);

         Books = repository.findAll();
         for(Book book : Books) {
             System.out.println(book);
         }
	}

}
