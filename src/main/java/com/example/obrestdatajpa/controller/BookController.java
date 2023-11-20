package com.example.obrestdatajpa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;

@RestController
public class BookController {
    // atributos
    private BookRepository bookRepository;

    private final Logger log = LoggerFactory.getLogger(BookController.class);

    //constructores
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Buscar todos los libros que hay en base de datos (ArrayList de libros)
     * http://localhost:8081/api/books
     */
    @GetMapping("/api/books")
    public List<Book> findAll() {
        // recuperar y devolver los libros de base de datos
        return this.bookRepository.findAll();
    }

    /**
     * Buscar un solo libro en base de datos segun su id
     * @param id
     * @return
     */
    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> findOneById(@PathVariable String id) {
        Optional<Book> book = this.bookRepository.findById(Long.valueOf(id));

        // Opcion 1
        // if (book.isPresent()) {
        //     return ResponseEntity.ok(book.get());
        // } else {
        //     return ResponseEntity.notFound().build();
        // }
        
        // Opcion 2
        // return book.orElse(null);

        // Opcion 3
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo libro en base de datos
    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers) {
        System.out.println(headers.get("User-Agent"));

        if(book.getId() != null) {
            log.warn("trying to create a book with id");
            return ResponseEntity.badRequest().build();
        }
        
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result);
    }

    // Actualizar un libro existente en base de datos
    @PutMapping("/api/books")
    public ResponseEntity<Book> update(@RequestBody Book book) {
        if(book.getId() == null) {
            log.warn("trying to update a non existent book");
            return ResponseEntity.badRequest().build();
        }

        if(!bookRepository.existsById(book.getId())) {
            log.warn("the book with id " + book.getId() + " non exist in data base");
            return ResponseEntity.notFound().build();
        }
        
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result);
    }
    

    // Borrar un libro en base de datos
    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id) {

        if(!bookRepository.existsById(id)) {
            log.warn("Trying to delete a non exist book");
            return ResponseEntity.notFound().build();
        }
        
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Borrar todos los libros
    @DeleteMapping("/api/books")
    public ResponseEntity<Book> deleteAll() {
        if(bookRepository.count() == 0) {
            log.warn("Trying to delete a empty book table");
            return ResponseEntity.notFound().build();
        }

        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
