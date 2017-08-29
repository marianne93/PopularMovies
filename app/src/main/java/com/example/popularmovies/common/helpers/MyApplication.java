package com.example.popularmovies.common.helpers;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Sherif.ElNady on 2/26/2017.
 */

public class MyApplication extends Application {

    private static Gson mGson;

    @Override
    public void onCreate() {
        super.onCreate();
        initGson();
    }

    private void initGson() {
        mGson = new GsonBuilder().create();
    }


    public static Gson getmGson() {
        return mGson;
    }


}
