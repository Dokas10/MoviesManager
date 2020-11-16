package com.andre.projects.moviesmanager.detail_activity.fragments;

import android.content.Intent;
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
import com.andre.projects.moviesmanager.detail_activity.ReviewsRecyclerViewAdapter;
import com.andre.projects.moviesmanager.detail_activity.network_response.ReviewResult;
import com.andre.projects.moviesmanager.detail_activity.utils.Mapper;
import com.andre.projects.moviesmanager.network.MovieApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsFragment extends Fragment {

    private View view;

    private ReviewsRecyclerViewAdapter adapterReviews;
    private RecyclerView rv_reviews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reviews, container, false);

        Intent intent = getActivity().getIntent();

        configureAdapterReviews();
        obtainReviews(intent.getStringExtra("Id"));
        return view;
    }

    private void configureAdapterReviews() {
        rv_reviews = view.findViewById(R.id.rv_reviews);
        rv_reviews.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterReviews = new ReviewsRecyclerViewAdapter();
        rv_reviews.setAdapter(adapterReviews);
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

    public void showError() {
        Toast.makeText(getActivity(), "Error occurred, please try again later.", Toast.LENGTH_SHORT).show();
    }
}