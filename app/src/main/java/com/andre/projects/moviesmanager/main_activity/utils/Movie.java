package com.andre.projects.moviesmanager.main_activity.utils;

//Class tha defines objects called Movie
public class Movie {

    private final String title;
    private final String posterPath;
    private final String resume;
    private final String rating;
    private final String releaseDate;
    private final String id;

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
