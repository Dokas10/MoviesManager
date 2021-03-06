package com.andre.projects.moviesmanager.detail_activity.network_response;

import com.squareup.moshi.Json;

import java.util.List;

//Class that manages results of the API call for the trailers
public class VideoResult {

    @Json(name="results")
    private final List<VideoResponse> resultVideos;

    public VideoResult(List<VideoResponse> resultVideos) {
        this.resultVideos = resultVideos;
    }

    public List<VideoResponse> getResultVideos() {
        return resultVideos;
    }

}
