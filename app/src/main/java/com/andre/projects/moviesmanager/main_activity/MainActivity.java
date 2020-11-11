package com.andre.projects.moviesmanager.main_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.andre.projects.moviesmanager.BuildConfig;
import com.andre.projects.moviesmanager.main_activity.utils.Movie;
import com.andre.projects.moviesmanager.detail_activity.MovieDetailsActivity;
import com.andre.projects.moviesmanager.main_activity.utils.MovieMapper;
import com.andre.projects.moviesmanager.R;
import com.andre.projects.moviesmanager.SettingsActivity;
import com.andre.projects.moviesmanager.network.FilmApiService;
import com.andre.projects.moviesmanager.main_activity.network.response.FilmResult;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    public static RecyclerViewAdapter adapter;
    public static RecyclerView rv;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    public List<Movie> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureAdapter();
        obtainMoviesByTopRated();

        drawer = findViewById(R.id.draw_lay);
        toggle = new ActionBarDrawerToggle(this, drawer,R.string.open_nav,R.string.close_nav);
        toggle.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.top_rated){
                    configureAdapter();
                    obtainMoviesByTopRated();
                } else if(id == R.id.popular){
                    configureAdapter();
                    obtainMoviesByPopularity();
                }else{

                }
                return true;
            }
        });

    }

    private void configureAdapter(){
        rv = findViewById(R.id.rv_movie);
        rv.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        adapter = new RecyclerViewAdapter();
        adapter.setClickListener(MainActivity.this);
        rv.setAdapter(adapter);
    }

    private void obtainMoviesByTopRated(){
        FilmApiService.getInstance().obtainMoviesTop(BuildConfig.MovieDBKey).enqueue(new Callback<FilmResult>() {
            @Override
            public void onResponse(Call<FilmResult> call, Response<FilmResult> response) {
                if (response.isSuccessful()){
                    mData = MovieMapper.responseToDomain(response.body().getResultadoFilmes());
                    adapter.setMovies(mData);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<FilmResult> call, Throwable t) {
                showError();
            }
        });
    }

    private void obtainMoviesByPopularity(){
        FilmApiService.getInstance().obtainMoviesPopular(BuildConfig.MovieDBKey).enqueue(new Callback<FilmResult>() {
            @Override
            public void onResponse(Call<FilmResult> call, Response<FilmResult> response) {
                if (response.isSuccessful()){
                    mData = MovieMapper.responseToDomain(response.body().getResultadoFilmes());
                    adapter.setMovies(mData);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<FilmResult> call, Throwable t) {
                showError();
            }
        });

    }

    public void showError(){
        Toast.makeText(this, "Error occurred, please try again later.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("Title", mData.get(position).getTitle());
        intent.putExtra("Thumbnail", mData.get(position).getPosterPath());
        intent.putExtra("Overview", mData.get(position).getResume());
        intent.putExtra("Rating", mData.get(position).getRating());
        intent.putExtra("Date", mData.get(position).getReleaseDate());
        intent.putExtra("Id", mData.get(position).getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}