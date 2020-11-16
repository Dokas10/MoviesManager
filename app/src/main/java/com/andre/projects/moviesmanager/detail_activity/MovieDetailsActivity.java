package com.andre.projects.moviesmanager.detail_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andre.projects.moviesmanager.BuildConfig;
import com.andre.projects.moviesmanager.R;
import com.andre.projects.moviesmanager.database.MovieContract;
import com.andre.projects.moviesmanager.database.MovieDBHelper;
import com.andre.projects.moviesmanager.detail_activity.fragments.DetailsFragment;
import com.andre.projects.moviesmanager.detail_activity.fragments.ReviewsFragment;
import com.andre.projects.moviesmanager.detail_activity.fragments.TrailersFragment;
import com.andre.projects.moviesmanager.detail_activity.network_response.ReviewResult;
import com.andre.projects.moviesmanager.detail_activity.network_response.VideoResult;
import com.andre.projects.moviesmanager.detail_activity.utils.Mapper;
import com.andre.projects.moviesmanager.detail_activity.utils.Video;
import com.andre.projects.moviesmanager.main_activity.MainActivity;
import com.andre.projects.moviesmanager.network.MovieApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private Intent intent;

    private MovieDBHelper mOpenHelper;

    BottomNavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mOpenHelper = new MovieDBHelper(this);

        nav_view = (BottomNavigationView) findViewById(R.id.nav_menu);
        nav_view.setOnNavigationItemSelectedListener(listener);

        intent = getIntent();

        getSupportFragmentManager().beginTransaction().replace(R.id.cons_layout_details, new DetailsFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch(item.getItemId()){

                case R.id.nav_details:
                    selectedFragment = new DetailsFragment();
                    break;

                case R.id.nav_reviews:
                    selectedFragment = new ReviewsFragment();
                    break;

                case R.id.nav_trailers:
                    selectedFragment = new TrailersFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.cons_layout_details, selectedFragment).commit();

            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }else if(id == R.id.action_add_favorites){
            databaseInsert();
        }


        return super.onOptionsItemSelected(item);
    }

    public void databaseInsert(){

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        String[] selectionArgs = {intent.getStringExtra("Id")};

        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.MOVIE_ID, intent.getStringExtra("Id"));
        cv.put(MovieContract.MovieEntry.MOVIE_TITLE, intent.getStringExtra("Title"));
        cv.put(MovieContract.MovieEntry.MOVIE_THUMBNAIL, intent.getStringExtra("Thumbnail"));
        cv.put(MovieContract.MovieEntry.MOVIE_OVERVIEW, intent.getStringExtra("Overview"));
        cv.put(MovieContract.MovieEntry.MOVIE_RATING, intent.getStringExtra("Rating"));
        cv.put(MovieContract.MovieEntry.MOVIE_DATE, intent.getStringExtra("Date"));
        long insertedRow = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, cv);

        if(insertedRow != -1)
            Toast.makeText(this, "Movie added to favorites successfully.", Toast.LENGTH_SHORT).show();
        else {
            db.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry.MOVIE_ID + "=" + intent.getStringExtra("Id"), null);
            Toast.makeText(this, "Movie removed from favorites.", Toast.LENGTH_SHORT).show();
        }

    }

}