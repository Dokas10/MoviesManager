package com.andre.projects.moviesmanager.detail_activity.network_response;

import com.squareup.moshi.Json;

import java.util.List;


//Class that manages results of the API call for the reviews
public class ReviewResult {

    @Json(name="results")
    private final List<ReviewResponse> resultReviews;

    public ReviewResult(List<ReviewResponse> resultadoFilmes) {
        this.resultReviews = resultadoFilmes;
    }

    public List<ReviewResponse> getResultReviews() {
        return resultReviews;
    }
}
