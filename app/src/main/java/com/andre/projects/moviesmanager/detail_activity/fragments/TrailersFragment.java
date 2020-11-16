package com.andre.projects.moviesmanager.detail_activity.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andre.projects.moviesmanager.BuildConfig;
import com.andre.projects.moviesmanager.R;
import com.andre.projects.moviesmanager.detail_activity.MovieDetailsActivity;
import com.andre.projects.moviesmanager.detail_activity.VideosRecyclerViewAdapter;
import com.andre.projects.moviesmanager.detail_activity.network_response.VideoResult;
import com.andre.projects.moviesmanager.detail_activity.utils.Mapper;
import com.andre.projects.moviesmanager.detail_activity.utils.Video;
import com.andre.projects.moviesmanager.main_activity.utils.Movie;
import com.andre.projects.moviesmanager.network.MovieApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailersFragment extends Fragment implements VideosRecyclerViewAdapter.ItemClickListener{

    private RecyclerView rv_videos;
    private VideosRecyclerViewAdapter adapterVideos;

    private View view;

    private List<Video> dataVideos;

    public TrailersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_trailers, container, false);

        Intent intent = getActivity().getIntent();

        configureAdapterVideos();
        obtainVideos(intent.getStringExtra("Id"));

        return view;
    }

    private void configureAdapterVideos() {
        rv_videos = view.findViewById(R.id.rv_videos);
        rv_videos.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterVideos = new VideosRecyclerViewAdapter();
        adapterVideos.setClickListener(TrailersFragment.this);
        rv_videos.setAdapter(adapterVideos);
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
        Toast.makeText(getActivity(), "Error occurred, please try again later.", Toast.LENGTH_SHORT).show();
    }

}