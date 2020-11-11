package com.andre.projects.moviesmanager.main_activity.network.response;

import com.squareup.moshi.Json;


public class MovieResponse {

    @Json(name = "poster_path")
    private final String path;

    @Json(name = "original_title")
    private final String title;

    @Json(name = "overview")
    private final String overview;

    @Json(name = "vote_average")
    private final String rating;

    @Json(name = "release_date")
    private final String releaseDate;

    @Json(name = "id")
    private final String id;

    public MovieResponse(String path, String title, String overview, String rating, String releaseDate, String id) {
        this.path = path;
        this.title = title;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getId() {
        return id;
    }
}
