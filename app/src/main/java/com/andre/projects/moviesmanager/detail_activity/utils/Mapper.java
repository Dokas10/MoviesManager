package com.andre.projects.moviesmanager.detail_activity.utils;

import com.andre.projects.moviesmanager.detail_activity.network_response.ReviewResponse;
import com.andre.projects.moviesmanager.detail_activity.network_response.VideoResponse;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static List<Review> responseToReview (List<ReviewResponse> reviewResponses){
        List<Review> lista = new ArrayList<>();

        for(ReviewResponse responses: reviewResponses)
            lista.add(new Review(responses.getAuthor(), responses.getContent()));

        return lista;
    }

    public static List<Video> responseToVideo (List<VideoResponse> videoResponses){

        List<Video> list = new ArrayList<>();

        for(VideoResponse video: videoResponses)
            list.add(new Video(video.getVideoName(), video.getKey()));

        return list;

    }

}
