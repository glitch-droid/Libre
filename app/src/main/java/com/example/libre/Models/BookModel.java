package com.example.libre.Models;

public class BookModel {

    String book;
    String author;
    public BookModel() {
    }

    public BookModel(String book, String author) {
        this.book = book;
        this.author = author;
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
}
