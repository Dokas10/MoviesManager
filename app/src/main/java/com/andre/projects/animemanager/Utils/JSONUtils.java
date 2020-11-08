package com.andre.projects.animemanager.Utils;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class JSONUtils {

    private static final String JSON_MESSAGE_CODE = "cod";

    private static final String JSON_TITLE = "title";
    private static final String JSON_IMAGE = "main_picture";

    private static final String JSON_LIST = "list";

    public static ContentValues[] getAnimeInfo(Context context, String animeInfoJson) throws JSONException {

        JSONObject animeFullJSON = new JSONObject(animeInfoJson);

        if(animeFullJSON.has(JSON_MESSAGE_CODE)){

            int errorCode = animeFullJSON.getInt(JSON_MESSAGE_CODE);

            switch (errorCode){

                case HttpURLConnection.HTTP_OK:
                    break;

                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;

                default:
                    return null;

            }

        }

        JSONArray animeJsonArray = animeFullJSON.getJSONArray(JSON_LIST);

        ContentValues[] animeContentValues = new ContentValues[animeJsonArray.length()];

        for(int i=0; i<animeJsonArray.length();i++){
            String title;

            JSONObject animeInfo = animeJsonArray.getJSONObject(i);

            title = animeInfo.getString(JSON_TITLE);

            ContentValues animeInfoContentValues = new ContentValues();
            animeInfoContentValues.put("TITLE", title);

            animeContentValues[i] = animeInfoContentValues;

        }

        return animeContentValues;

    }

}
