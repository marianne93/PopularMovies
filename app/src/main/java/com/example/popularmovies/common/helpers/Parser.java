package com.example.popularmovies.common.helpers;

import com.example.popularmovies.common.models.dto.MoviesServiceResponse;

/**
 * Created by Marianne.Wazif on 27-Aug-17.
 */

public class Parser {
    private static Parser mInstance;

    public static synchronized Parser getInstance() {
        if (mInstance == null) {
            mInstance = new Parser();
        }
        return mInstance;
    }

    private Parser() {
    }
    public MoviesServiceResponse getMoviesResponse(String moviesResponseString) {
        return MyApplication.getmGson().fromJson(moviesResponseString, MoviesServiceResponse.class);
    }
}
