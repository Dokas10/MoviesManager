package com.andre.projects.moviesmanager.network;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MovieApiService {

    private static MovieInterface INSTANCE;

    public static MovieInterface getInstance(){

        if(INSTANCE == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();

            INSTANCE = retrofit.create(MovieInterface.class);

        }

        return INSTANCE;

    }

}
