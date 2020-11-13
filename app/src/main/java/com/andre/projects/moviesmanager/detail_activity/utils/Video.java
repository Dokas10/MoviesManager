package com.andre.projects.moviesmanager.detail_activity.utils;

public class Video {

    private String name;
    private String key;

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
