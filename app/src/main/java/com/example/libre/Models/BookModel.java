package com.example.libre.Models;

public class BookModel {

    String book;
    String author;
    String description;
    String price;
    String url;
    String id;
    public BookModel() {
    }

    public BookModel(String book, String author, String description, String price,String url,String id) {
        this.book = book;
        this.author = author;
        this.description = description;
        this.price = price;
        this.id=id;
        this.url=url;
    }

    //Constructor for testing purposes
    public BookModel(String book,  String price) {
        this.book = book;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
