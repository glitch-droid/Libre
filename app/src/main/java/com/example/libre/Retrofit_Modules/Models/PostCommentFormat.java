package com.example.libre.Retrofit_Modules.Models;

public class PostCommentFormat {
    String comment;

    public PostCommentFormat(String commentBody) {
        this.comment=commentBody;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
