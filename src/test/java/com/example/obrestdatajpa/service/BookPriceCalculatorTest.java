package com.example.obrestdatajpa.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import com.example.obrestdatajpa.entities.Book;

import org.junit.jupiter.api.Test;

class BookPriceCalculatorTest {

    @Test
    void calculatorPriceTest() {

        // configuracion de la prueba
        Book book = new Book(1L, "Tokyo Blues", "Murakami", 1000, 49.99, LocalDate.now(), true);
        BookPriceCalculator calculator = new BookPriceCalculator();

        //se ejecuta el comportamiento a testear
        double price = calculator.calculatorPrice(book);
        System.out.println(price);

        //assertions
        assertTrue(price > 0);
        assertEquals(price, 52.980000000000004);
    }
}
