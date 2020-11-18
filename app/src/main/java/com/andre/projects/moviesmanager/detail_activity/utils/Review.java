package com.andre.projects.moviesmanager.detail_activity.utils;

// Class that defines object of type Review
public class Review {

    private final String author;
    private final String content;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
