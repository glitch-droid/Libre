package com.example.libre.Retrofit_Modules.Models;

public class FetchedComment {
    private CommentsAuthor author;
    private String _id;
    private String comment;
    private String date;
    private String __v;

    public CommentsAuthor getAuthor() {
        return author;
    }

    public void setAuthor(CommentsAuthor author) {
        this.author = author;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }
}
