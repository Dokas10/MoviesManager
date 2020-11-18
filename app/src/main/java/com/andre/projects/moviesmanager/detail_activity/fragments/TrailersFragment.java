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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andre.projects.moviesmanager.BuildConfig;
import com.andre.projects.moviesmanager.R;
import com.andre.projects.moviesmanager.detail_activity.VideosRecyclerViewAdapter;
import com.andre.projects.moviesmanager.detail_activity.network_response.VideoResult;
import com.andre.projects.moviesmanager.detail_activity.utils.Mapper;
import com.andre.projects.moviesmanager.detail_activity.utils.Video;
import com.andre.projects.moviesmanager.network.MovieApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailersFragment extends Fragment implements VideosRecyclerViewAdapter.ItemClickListener{

    private VideosRecyclerViewAdapter adapterVideos;

    private View view;

    private List<Video> dataVideos;

    private TextView error;
    private TextView vid;
    private LinearLayout layout;

    //Inflate trailers layout and set content in views
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_trailers, container, false);

        Intent intent = getActivity().getIntent();

        error = (TextView) view.findViewById(R.id.trailers_error);
        vid = (TextView) view.findViewById(R.id.videos);
        layout = (LinearLayout) view.findViewById(R.id.trail_lay);

        error.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
        vid.setVisibility(View.VISIBLE);

        configureAdapterVideos();
        obtainVideos(intent.getStringExtra("Id"));

        return view;
    }

    //Method to configure trailers adapter for recyclerview
    private void configureAdapterVideos() {

        RecyclerView rv_videos;
        rv_videos = view.findViewById(R.id.rv_videos);
        rv_videos.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterVideos = new VideosRecyclerViewAdapter();
        adapterVideos.setClickListener(TrailersFragment.this);
        rv_videos.setAdapter(adapterVideos);
    }

    //Method to obtain the trailers based on movie id
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

    //Method to send user to youtube after clicking the trailer
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + dataVideos.get(position).getKey()));
        startActivity(intent);
    }

    //Method to show error message
    public void showError() {
        layout.setVisibility(View.INVISIBLE);
        error.setVisibility(View.VISIBLE);
        vid.setVisibility(View.INVISIBLE);
    }

}