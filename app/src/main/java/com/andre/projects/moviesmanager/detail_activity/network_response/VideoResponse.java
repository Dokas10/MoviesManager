package com.andre.projects.moviesmanager.detail_activity.network_response;

import com.squareup.moshi.Json;

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
