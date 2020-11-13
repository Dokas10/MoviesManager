package com.andre.projects.moviesmanager.database;

import android.provider.BaseColumns;

public class MovieContract {

    public static final class MovieEntry implements BaseColumns{

        public static final String TABLE_NAME = "movies";

        public static final String MOVIE_ID = "movie_id";

        public static final String MOVIE_TITLE = "title";

        public static final String MOVIE_THUMBNAIL = "thumbnail";

        public static final String MOVIE_OVERVIEW = "overview";

        public static final String MOVIE_RATING = "rating";

        public static final String MOVIE_DATE = "release_date";



    }

}
