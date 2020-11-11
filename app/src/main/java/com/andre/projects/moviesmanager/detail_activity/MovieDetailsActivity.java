package com.andre.projects.moviesmanager.detail_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andre.projects.moviesmanager.BuildConfig;
import com.andre.projects.moviesmanager.R;
import com.andre.projects.moviesmanager.detail_activity.network_response.ReviewResult;
import com.andre.projects.moviesmanager.detail_activity.utils.ReviewMapper;
import com.andre.projects.moviesmanager.main_activity.MainActivity;
import com.andre.projects.moviesmanager.main_activity.RecyclerViewAdapter;
import com.andre.projects.moviesmanager.network.FilmApiService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView poster;
    TextView title;
    TextView resume;
    TextView ratings;
    TextView releaseDate;

    private RecyclerView rv;
    private ReviewsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        poster = (ImageView) findViewById(R.id.poster);
        title = (TextView) findViewById(R.id.movie_name);
        resume = (TextView) findViewById(R.id.resume_text);
        ratings = (TextView) findViewById(R.id.rating);
        releaseDate = (TextView) findViewById(R.id.releaseDate);

        Intent intent = getIntent();

        Picasso.with(this).load("https://image.tmdb.org/t/p/w500" + intent.getStringExtra("Thumbnail")).into(poster);
        title.setText(intent.getStringExtra("Title"));
        resume.setText(intent.getStringExtra("Overview"));
        ratings.setText("User Rating: " + intent.getStringExtra("Rating"));
        releaseDate.setText("Release Date: " + intent.getStringExtra("Date"));

        configureAdapter();
        obtainReviews(intent.getStringExtra("Id"));

    }

    private void configureAdapter(){
        rv = findViewById(R.id.rv_reviews);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReviewsRecyclerViewAdapter();
        rv.setAdapter(adapter);
    }

    private void obtainReviews(String id){
        FilmApiService.getInstance().obtainReviews(id, BuildConfig.MovieDBKey).enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                if(response.isSuccessful()){
                    adapter.setReviews(ReviewMapper.responseToReview(response.body().getResultReviews()));
                }else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {
                 showError();
            }
        });
    }

    public void showError(){
        Toast.makeText(this, "Error occurred, please try again later.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }
}