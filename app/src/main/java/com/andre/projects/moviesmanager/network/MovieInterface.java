package com.andre.projects.moviesmanager.network;

import com.andre.projects.moviesmanager.detail_activity.network_response.ReviewResult;
import com.andre.projects.moviesmanager.detail_activity.network_response.VideoResult;
import com.andre.projects.moviesmanager.main_activity.network.response.FilmResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieInterface {

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResult> obtainReviews(@Path("movie_id") String id, @Query("api_key") String key);

    @GET("movie/{movie_id}/videos")
    Call<VideoResult> obtainVideos(@Path("movie_id") String id, @Query("api_key") String key);

    @GET("movie/popular")
    Call<FilmResult> obtainMoviesPopular(@Query("api_key") String key);

    @GET("movie/top_rated")
    Call<FilmResult> obtainMoviesTop(@Query("api_key") String key);

    @GET("search/movie")
    Call<FilmResult> obtainMoviesBySearch(@Query("query") String search, @Query("api_key") String key);

}
