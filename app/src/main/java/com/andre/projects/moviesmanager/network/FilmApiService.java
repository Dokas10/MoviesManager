package com.andre.projects.moviesmanager.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmApiService {

    private static FilmInterface INSTANCE;

    public static FilmInterface getInstance(){

        if(INSTANCE == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.myanimelist.net/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            INSTANCE = retrofit.create(FilmInterface.class);

        }

        return INSTANCE;

    }

}
