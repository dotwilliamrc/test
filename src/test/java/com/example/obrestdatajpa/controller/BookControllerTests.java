package com.example.obrestdatajpa.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.obrestdatajpa.entities.Book;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookControllerTests {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;
    
    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @DisplayName("Comprobar hola mundo desde controladores Spring REST")
    @Test
    void hello() {
        ResponseEntity<String> response =
            this.testRestTemplate.getForEntity("/hello", String.class);
        
        assertEquals(HttpStatus.OK ,response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Hello World!!!!!", response.getBody());
    }
    
    @Test
    void findAll() {
        ResponseEntity<Book[]> response =
            testRestTemplate.getForEntity("/api/books", Book[].class);

        assertEquals(HttpStatus.OK ,response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        
        List<Book> books = Arrays.asList(response.getBody());
        System.out.println(books.size());
    }

    @Test
    void findOneById() {
        ResponseEntity<Book> response =
            this.testRestTemplate.getForEntity("/api/books/1", Book.class);
        
        //assertEquals(HttpStatus.NOT_FOUND ,response.getStatusCode());
        //assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
            {
                "title": "Libro nuevo",
                "author": "Yuval Noah",
                "pages": 650,
                "price": 19.99,
                "releaseDate": "2019-12-01",
                "online": false
            }
        """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Book> response = testRestTemplate.exchange("/api/books", HttpMethod.POST, request, Book.class);

        Book result = response.getBody();

        assertEquals(1L, result.getId());
        assertEquals("Libro nuevo", result.getTitle());
    }
}
