package com.example.obrestdatajpa.service;

import com.example.obrestdatajpa.entities.Book;

public class BookPriceCalculator {

    public double calculatorPrice(Book book) {
        double price = book.getPrice();

        if(book.getPages() > 1000) {
            price += 5;
        }
        price += 2.99;
        return price;
    }
}
