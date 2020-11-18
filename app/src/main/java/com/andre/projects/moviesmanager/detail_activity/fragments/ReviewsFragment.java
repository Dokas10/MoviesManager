package com.andre.projects.moviesmanager.detail_activity.fragments;

import android.content.Intent;
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
import com.andre.projects.moviesmanager.detail_activity.ReviewsRecyclerViewAdapter;
import com.andre.projects.moviesmanager.detail_activity.network_response.ReviewResult;
import com.andre.projects.moviesmanager.detail_activity.utils.Mapper;
import com.andre.projects.moviesmanager.network.MovieApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsFragment extends Fragment {

    private View view;

    private TextView error;
    private TextView rev;
    private LinearLayout layout;

    private ReviewsRecyclerViewAdapter adapterReviews;

    //Details fragment inflater and view content set
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_reviews, container, false);

        layout = (LinearLayout) view.findViewById(R.id.lay_rev);
        error = (TextView) view.findViewById(R.id.rev_error);
        rev = (TextView) view.findViewById(R.id.reviews);

        error.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
        rev.setVisibility(View.VISIBLE);

        Intent intent = getActivity().getIntent();

        configureAdapterReviews();
        obtainReviews(intent.getStringExtra("Id"));
        return view;
    }

    //Method to configure reviews recyclerview adapter
    private void configureAdapterReviews() {

        RecyclerView rv_reviews;
        rv_reviews = view.findViewById(R.id.rv_reviews);
        rv_reviews.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterReviews = new ReviewsRecyclerViewAdapter();
        rv_reviews.setAdapter(adapterReviews);
    }

    //Method to obtain reviews based on movie id
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

    //Method to show message to show connection error
    public void showError() {

        layout.setVisibility(View.INVISIBLE);
        error.setVisibility(View.VISIBLE);
        rev.setVisibility(View.INVISIBLE);

    }
}