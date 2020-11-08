package com.andre.projects.animemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.andre.projects.animemanager.Utils.JSONUtils;
import com.google.gson.internal.$Gson$Types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{

    RecyclerViewAdapter adapter;
    String[] data;
    ArrayList<String> auxData = new ArrayList<String>();
    RecyclerView rv;
    //String[] data = {"anime1", "anime2", "anime3", "anime4", "anime5", "anime6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv_anime);
        int numberOfColumns = 2;
        rv.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        adapter = new RecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        rv.setAdapter(adapter);


    }

    public class FetchAnimeData extends AsyncTask<String, Void, String[]>{

        @Override
        protected String[] doInBackground(String... strings) {
            try{
                data = presentAnimeImages();
            }catch (JSONException e){
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String[] strings) {

            if(strings != null){
                for(String animeString : strings){
                    adapter = new RecyclerViewAdapter(MainActivity.this, data);
                    adapter.setClickListener(MainActivity.this);
                    rv.setAdapter(adapter);
                }
            }
        }
    }

    public String[] presentAnimeImages() throws JSONException {
        String[] data1;

        try {
            URL animeRequestUrl = MalApiResolver.buildUrlForList("one","title,main_picture");
            String jsonAnimeResponse = MalApiResolver.getResponseFromHttpUrl(animeRequestUrl);

            ContentValues[] animeTitles = JSONUtils.getAnimeInfo(this, jsonAnimeResponse);

            for (int i=0; i<animeTitles.length; i++){
                auxData.add(animeTitles[i].getAsString("TITLE"));
            }

            data1 = new String[auxData.size()];
            data1 = auxData.toArray(data1);

            return data1;

        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
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