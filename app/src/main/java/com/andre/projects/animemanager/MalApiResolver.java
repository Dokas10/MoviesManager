package com.andre.projects.animemanager;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MalApiResolver {

    private static final String ANIME_URL = "https://api.myanimelist.net/v2";

    private static final int limit = 100;
    private static final int id = 0;
    private static final int offset = 0;

    private static final String QUERY_PARAM = "q";
    private static final String ID_PARAM = "id";
    private static final String LIMIT_PARAM = "limit";
    private static final String OFFSET_PARAM = "offset";
    private static final String FIELDS_PARAM = "fields";

    public static URL buildUrlForList(String animeName, String fields){

        Uri builtUri = Uri.parse(ANIME_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, animeName)
                .appendQueryParameter(LIMIT_PARAM, Integer.toString(limit))
                .appendQueryParameter(FIELDS_PARAM, fields)
                .build();

        URL url = null;

        try{
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        Log.i("URL", "Built Uri: "+ url);
        return url;

    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
