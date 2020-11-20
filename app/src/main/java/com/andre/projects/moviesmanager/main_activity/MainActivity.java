package com.andre.projects.moviesmanager.main_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.andre.projects.moviesmanager.BuildConfig;
import com.andre.projects.moviesmanager.database.MovieContract;
import com.andre.projects.moviesmanager.database.MovieDBHelper;
import com.andre.projects.moviesmanager.main_activity.utils.Movie;
import com.andre.projects.moviesmanager.detail_activity.MovieDetailsActivity;
import com.andre.projects.moviesmanager.main_activity.utils.MovieMapper;
import com.andre.projects.moviesmanager.R;
import com.andre.projects.moviesmanager.network.MovieApiService;
import com.andre.projects.moviesmanager.main_activity.network.response.FilmResult;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    public static RecyclerViewAdapter adapter;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    public List<Movie> mData;
    private SQLiteOpenHelper mOpenHelper;
    private TextView error;
    int i=1;
    private static boolean IS_FAVORITE_SELECTED = false;
    private RelativeLayout layout;

    private final String KEY_STATE = "key_state";
    private static Bundle mBundleRecyclerViewState;

    public static GridLayoutManager mLayoutManager;
    public RecyclerView rv;

    private static int numberGridColumns;

    //Instances of all views and treatment of navigation bar clicks
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        ab.setDisplayHomeAsUpEnabled(true);

        mOpenHelper = new MovieDBHelper(this);
        mData = new ArrayList<>();

        error = (TextView) findViewById(R.id.error_tv);
        layout = (RelativeLayout) findViewById(R.id.lay_mov);
        error.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            numberGridColumns = 3;
        else
            numberGridColumns = 2;

        configureAdapter(numberGridColumns);
        obtainMoviesByPopularity(i);
        rvScrollListenerPopularity();

        drawer = findViewById(R.id.draw_lay);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open_nav, R.string.close_nav);
        toggle.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.top_rated) {
                    IS_FAVORITE_SELECTED = false;
                    configureAdapter(numberGridColumns);
                    obtainMoviesByTopRated(1);
                    rvScrollListenerTop();
                } else if (id == R.id.popular) {
                    IS_FAVORITE_SELECTED = false;
                    configureAdapter(numberGridColumns);
                    obtainMoviesByPopularity(1);
                    rvScrollListenerPopularity();
                } else {
                    IS_FAVORITE_SELECTED = true;
                    configureAdapter(numberGridColumns);
                    mData = obtainMoviesByFavourite();
                    adapter.setMovies(mData);
                }
                return true;
            }
        });
    }

    //Method to detect recycler view scroll reaching top or bottom for popular movies and change pages
    private void rvScrollListenerPopularity() {
        i = 1;
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!rv.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && !IS_FAVORITE_SELECTED) {
                    if (i == 1000)
                        return;
                    else
                        i++;
                    rv.scrollToPosition(0);
                    obtainMoviesByPopularity(i);
                } else if (!rv.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE && !IS_FAVORITE_SELECTED) {
                    if (i == 1)
                        return;
                    else
                        i--;
                    rv.scrollToPosition(adapter.getItemCount() - 1);
                    obtainMoviesByPopularity(i);
                }
            }
        });
    }

    //Method to detect recycler view scroll reaching top or bottom for top rated movies and change pages
    private void rvScrollListenerTop(){
        i=1;
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!rv.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE && !IS_FAVORITE_SELECTED) {
                    if(i == 1000)
                        return;
                    else
                        i++;
                    rv.scrollToPosition(0);
                    obtainMoviesByTopRated(i);
                } else if(!rv.canScrollVertically(-1) && newState==RecyclerView.SCROLL_STATE_IDLE && !IS_FAVORITE_SELECTED) {
                    if(i == 1)
                        return;
                    else
                        i--;
                    rv.scrollToPosition(adapter.getItemCount()-1);
                    obtainMoviesByTopRated(i);
                }
            }
        });
    }

    //Method to configure adapter for the movie RecyclerView
    private void configureAdapter(int columnsGrid){
        rv = findViewById(R.id.rv_movie);
        mLayoutManager = new GridLayoutManager(MainActivity.this, columnsGrid);
        rv.setLayoutManager(mLayoutManager);

        adapter = new RecyclerViewAdapter();
        adapter.setClickListener(MainActivity.this);
        rv.setAdapter(adapter);
    }

    //Method to obtain top rated movies from API database
    private void obtainMoviesByTopRated(int i){
        MovieApiService.getInstance().obtainMoviesTop(i, BuildConfig.MovieDBKey).enqueue(new Callback<FilmResult>() {
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

    //Method to obtain Popular movies from API database
    private void obtainMoviesByPopularity(int i){
        MovieApiService.getInstance().obtainMoviesPopular(i, BuildConfig.MovieDBKey).enqueue(new Callback<FilmResult>() {
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

    //Method to obtain favorite movies from local database
    private List<Movie> obtainMoviesByFavourite(){

        List<Movie> favorites = new ArrayList<>();

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        Cursor cursor = db.query(MovieContract.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()){
            Movie movie = new Movie(cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(0));

            favorites.add(movie);

        }

        return favorites;

    }

    //Method to obtain movies by search from API database
    private void obtainMovieBySearch(String search){
        MovieApiService.getInstance().obtainMoviesBySearch(search, BuildConfig.MovieDBKey).enqueue(new Callback<FilmResult>() {
            @Override
            public void onResponse(Call<FilmResult> call, Response<FilmResult> response) {
                if(response.isSuccessful()) {
                    mData = MovieMapper.responseToDomain(response.body().getResultadoFilmes());
                    adapter.setMovies(mData);
                }else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<FilmResult> call, Throwable t) {
                showError();
            }
        });
    }

    //Method that shows error messages
    public void showError(){
        error.setVisibility(View.VISIBLE);
        layout.setVisibility(View.INVISIBLE);

    }

    //OnClick handler for all RecyclerView itens
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

    //Create main menu layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                configureAdapter(numberGridColumns);
                obtainMovieBySearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    //Treatment of action click for all itens in the action bar and navigation bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_refresh){
            finish();
            overridePendingTransition(0,0);
            startActivity(getIntent());
            overridePendingTransition(0,0);
        }

        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = rv.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_STATE, listState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_STATE);
            rv.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
}