package com.andre.projects.moviesmanager.network;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class FilmApiService {

    private static FilmInterface INSTANCE;

    public static FilmInterface getInstance(){

        if(INSTANCE == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();

            INSTANCE = retrofit.create(FilmInterface.class);

        }

        return INSTANCE;

    }

}
