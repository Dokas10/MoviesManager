package com.andre.projects.moviesmanager.detail_activity.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andre.projects.moviesmanager.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailsFragment extends Fragment {

    View view;
    ImageView poster;
    TextView title;
    TextView resume;
    TextView ratings;
    TextView releaseDate;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_details, container, false);

        poster = (ImageView) view.findViewById(R.id.poster);
        title = (TextView) view.findViewById(R.id.movie_name);
        resume = (TextView) view.findViewById(R.id.resume_text);
        ratings = (TextView) view.findViewById(R.id.rating);
        releaseDate = (TextView) view.findViewById(R.id.releaseDate);

        Intent intent = getActivity().getIntent();

        Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w500" + intent.getStringExtra("Thumbnail")).transform(new RoundedCornersTransformation(10,10)).into(poster);
        title.setText(intent.getStringExtra("Title"));
        resume.setText(intent.getStringExtra("Overview"));
        ratings.setText("User Rating: " + intent.getStringExtra("Rating"));
        releaseDate.setText("Release Date: " + intent.getStringExtra("Date"));

        return view;

    }
}