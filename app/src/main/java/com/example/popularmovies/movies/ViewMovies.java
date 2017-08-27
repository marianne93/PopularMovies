package com.example.popularmovies.movies;

import com.example.popularmovies.common.base.ViewBase;
import com.example.popularmovies.common.models.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marianne.Wazif on 27-Aug-17.
 */

public interface ViewMovies extends ViewBase {
    void onMoviesLoadedSucceed(List<Result> moviesList);
    void onMoviesError(String error);
}
