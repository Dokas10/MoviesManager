package com.andre.projects.moviesmanager.detail_activity.network_response;

import com.squareup.moshi.Json;

//Class that manages trailer attributes based on the API call
public class VideoResponse {

    @Json(name="name")
    private final String name;

    @Json(name="key")
    private final String key;

    public VideoResponse(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getVideoName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
