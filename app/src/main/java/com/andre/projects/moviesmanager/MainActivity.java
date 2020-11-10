package com.andre.projects.moviesmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.andre.projects.moviesmanager.network.FilmApiService;
import com.andre.projects.moviesmanager.network.response.FilmResponse;
import com.andre.projects.moviesmanager.network.response.FilmResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{

    public static RecyclerViewAdapter adapter;
    public static RecyclerView rv;
    List<FilmResponse> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv_anime);
        int numberOfColumns = 2;
        rv.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        FilmApiService.getInstance().obtainAnime(String.valueOf(R.string.api_key_client_id)).enqueue(new Callback<FilmResult>() {
            @Override
            public void onResponse(Call<FilmResult> call, Response<FilmResult> response) {
                if (response.isSuccessful()){
                    data = response.body().getData();
                }
            }

            @Override
            public void onFailure(Call<FilmResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error while communicating with API", Toast.LENGTH_SHORT);
            }
        });

        adapter = new RecyclerViewAdapter(MainActivity.this, data);
        adapter.setClickListener(MainActivity.this);
        rv.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();

        if(uri != null)
            Toast.makeText(this, "Coisas vivas", Toast.LENGTH_SHORT);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
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

        return super.onOptionsItemSelected(item);
    }
}