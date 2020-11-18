package com.andre.projects.moviesmanager.main_activity.network.response;

import com.squareup.moshi.Json;

import java.util.List;

//Class that manages of the results for the movies
public class FilmResult {

    @Json(name="results")
    private final List<MovieResponse> resultadoFilmes;

    public FilmResult(List<MovieResponse> resultadoFilmes) {
        this.resultadoFilmes = resultadoFilmes;
    }

    public List<MovieResponse> getResultadoFilmes() {
        return resultadoFilmes;
    }

}
