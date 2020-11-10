package com.andre.projects.moviesmanager.network;

import com.andre.projects.moviesmanager.network.response.FilmResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmInterface {

    @GET("/anime")
    Call<FilmResult> obtainAnime(@Query("api_key") String api_key);

}
