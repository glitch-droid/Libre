package com.example.libre.Models;

public class CommentModel {
    String userName;
    String comment;
    String commenterId;
    String currentUserId;
    String commentID;

    public CommentModel() {
    }

    public CommentModel(String userName, String comment,String commenterId,String currentUserId,String commentID) {
        this.userName = userName;
        this.comment = comment;
        this.commenterId=commenterId;
        this.currentUserId=currentUserId;
        this.commentID=commentID;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public String getCommenterId() {
        return commenterId;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void setCommenterId(String commenterId) {
        this.commenterId = commenterId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
