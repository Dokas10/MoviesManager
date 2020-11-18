package com.andre.projects.moviesmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 1;


    //Database Constructor
    public MovieDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Execute method for table creation in database
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry.MOVIE_ID + " TEXT PRIMARY KEY, " +
                MovieContract.MovieEntry.MOVIE_TITLE + " TEXT, " +
                MovieContract.MovieEntry.MOVIE_THUMBNAIL + " TEXT, " +
                MovieContract.MovieEntry.MOVIE_OVERVIEW + " TEXT, " +
                MovieContract.MovieEntry.MOVIE_RATING + " TEXT, " +
                MovieContract.MovieEntry.MOVIE_DATE + " TEXT);";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);

    }


    //Drops database table if it is updated
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
