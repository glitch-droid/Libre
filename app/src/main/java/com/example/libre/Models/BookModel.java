package com.example.libre.Models;

public class BookModel {

    String book;
    String author;
    String description;
    String price;
    public BookModel() {
    }

    public BookModel(String book, String author, String description, String price) {
        this.book = book;
        this.author = author;
        this.description = description;
        this.price = price;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
