package com.andre.projects.moviesmanager.detail_activity.network_response;

import com.squareup.moshi.Json;

public class ReviewResponse {

    @Json(name = "author")
    private final String author;

    @Json(name = "content")
    private final String content;

    public ReviewResponse(String author, String content) {
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
