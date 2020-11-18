package com.andre.projects.moviesmanager.detail_activity.utils;

// Class that defines object of type Video
public class Video {

    private final String name;
    private final String key;

    public Video(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
