package com.andre.projects.moviesmanager.network.response;

import com.google.gson.annotations.SerializedName;


public class FilmResponse {

    @SerializedName("node")
    private final String node;

    @SerializedName("title")
    private final String title;


    public FilmResponse(String node, String title) {
        this.node = node;
        this.title = title;
    }

    public String getNode() {
        return node;
    }

    public String getTitle() {
        return title;
    }
}
