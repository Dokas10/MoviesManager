package com.andre.projects.moviesmanager.detail_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.andre.projects.moviesmanager.detail_activity.network_response.ReviewResult;
import com.andre.projects.moviesmanager.detail_activity.network_response.VideoResult;
import com.andre.projects.moviesmanager.detail_activity.utils.Mapper;
import com.andre.projects.moviesmanager.detail_activity.utils.Video;
import com.andre.projects.moviesmanager.main_activity.MainActivity;
import com.andre.projects.moviesmanager.network.MovieApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity implements VideosRecyclerViewAdapter.ItemClickListener {

    ImageView poster;
    TextView title;
    TextView resume;
    TextView ratings;
    TextView releaseDate;

    private List<Video> dataVideos;

    private RecyclerView rv_reviews;
    private RecyclerView rv_videos;
    private ReviewsRecyclerViewAdapter adapterReviews;
    private VideosRecyclerViewAdapter adapterVideos;
    private Intent intent;

    private MovieDBHelper mOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mOpenHelper = new MovieDBHelper(this);

        poster = (ImageView) findViewById(R.id.poster);
        title = (TextView) findViewById(R.id.movie_name);
        resume = (TextView) findViewById(R.id.resume_text);
        ratings = (TextView) findViewById(R.id.rating);
        releaseDate = (TextView) findViewById(R.id.releaseDate);

        intent = getIntent();

        Picasso.with(this).load("https://image.tmdb.org/t/p/w500" + intent.getStringExtra("Thumbnail")).into(poster);
        title.setText(intent.getStringExtra("Title"));
        resume.setText(intent.getStringExtra("Overview"));
        ratings.setText("User Rating: " + intent.getStringExtra("Rating"));
        releaseDate.setText("Release Date: " + intent.getStringExtra("Date"));

        configureAdapterReviews();
        obtainReviews(intent.getStringExtra("Id"));

        configureAdapterVideos();
        obtainVideos(intent.getStringExtra("Id"));

    }

    private void configureAdapterReviews() {
        rv_reviews = findViewById(R.id.rv_reviews);
        rv_reviews.setLayoutManager(new LinearLayoutManager(this));

        adapterReviews = new ReviewsRecyclerViewAdapter();
        rv_reviews.setAdapter(adapterReviews);
    }

    private void configureAdapterVideos() {
        rv_videos = findViewById(R.id.rv_videos);
        rv_videos.setLayoutManager(new LinearLayoutManager(this));

        adapterVideos = new VideosRecyclerViewAdapter();
        adapterVideos.setClickListener(MovieDetailsActivity.this);
        rv_videos.setAdapter(adapterVideos);
    }

    private void obtainReviews(String id) {
        MovieApiService.getInstance().obtainReviews(id, BuildConfig.MovieDBKey).enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                if (response.isSuccessful()) {
                    adapterReviews.setReviews(Mapper.responseToReview(response.body().getResultReviews()));
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {
                showError();
            }
        });
    }

    private void obtainVideos(String id) {

        MovieApiService.getInstance().obtainVideos(id, BuildConfig.MovieDBKey).enqueue(new Callback<VideoResult>() {
            @Override
            public void onResponse(Call<VideoResult> call, Response<VideoResult> response) {
                if(response.isSuccessful()){
                    dataVideos = Mapper.responseToVideo(response.body().getResultVideos());
                    adapterVideos.setVideo(dataVideos);
                }else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<VideoResult> call, Throwable t) {
                showError();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + dataVideos.get(position).getKey()));
        startActivity(intent);
    }

    public void showError() {
        Toast.makeText(this, "Error occurred, please try again later.", Toast.LENGTH_SHORT).show();
    }

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