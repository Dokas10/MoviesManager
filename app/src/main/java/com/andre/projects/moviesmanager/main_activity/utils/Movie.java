package com.andre.projects.moviesmanager.main_activity.utils;

public class Movie {

    private String title;
    private String posterPath;
    private String resume;
    private String rating;
    private String releaseDate;
    private String id;

    public Movie(String title, String posterPath, String resume, String rating, String releaseDate, String id) {
        this.title = title;
        this.posterPath = posterPath;
        this.resume = resume;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getResume() {
        return resume;
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
