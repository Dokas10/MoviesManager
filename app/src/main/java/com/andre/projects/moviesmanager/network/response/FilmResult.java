package com.andre.projects.moviesmanager.network.response;

import com.squareup.moshi.Json;

import java.util.List;

public class FilmResult {

    @Json(name="data")
    private final List<FilmResponse> data;

    public FilmResult(List<FilmResponse> data) {
        this.data = data;
    }

    public List<FilmResponse> getData() {
        return data;
    }
}
