package com.andre.projects.moviesmanager.main_activity.utils;

import com.andre.projects.moviesmanager.main_activity.network.response.MovieResponse;

import java.util.ArrayList;
import java.util.List;

public class MovieMapper {

    public static List<Movie> responseToDomain(List<MovieResponse> responseList){

        List<Movie> movieList = new ArrayList<>();

        for (MovieResponse movieResponse : responseList){
            final Movie movie = new Movie(movieResponse.getTitle(), movieResponse.getPath(), movieResponse.getOverview(), movieResponse.getRating(), movieResponse.getReleaseDate(), movieResponse.getId());
            movieList.add(movie);
        }

        return movieList;

    }

}
