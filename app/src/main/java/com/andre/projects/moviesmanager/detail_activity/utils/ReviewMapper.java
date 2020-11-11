package com.andre.projects.moviesmanager.detail_activity.utils;

import com.andre.projects.moviesmanager.detail_activity.network_response.ReviewResponse;

import java.util.ArrayList;
import java.util.List;

public class ReviewMapper {

    public static List<Review> responseToReview (List<ReviewResponse> reviewResponses){
        List<Review> lista = new ArrayList<>();

        for(ReviewResponse responses: reviewResponses)
            lista.add(new Review(responses.getAuthor(), responses.getContent()));

        return lista;
    }
}
