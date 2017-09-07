package com.example.popularmovies.common.helpers;

import android.text.TextUtils;

import com.example.popularmovies.common.models.Movie;
import com.example.popularmovies.common.models.dto.MoviesServiceResponse;
import com.example.popularmovies.common.models.dto.MoviesTrailersServiceResponse;

import java.util.ArrayList;
import java.util.Arrays;

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

    public MoviesTrailersServiceResponse getMoviesTrailersResponse(String moviesTrailersResponseString) {
        return MyApplication.getmGson().fromJson(moviesTrailersResponseString, MoviesTrailersServiceResponse.class);
    }

    public ArrayList<Movie> getFavoriteMovies(String favoriteMoviesStr) {
        if (TextUtils.isEmpty(favoriteMoviesStr))
            return new ArrayList<>();
        else
            return new ArrayList<>(Arrays.asList(MyApplication.getmGson().fromJson(favoriteMoviesStr, Movie[].class)));
    }
}
