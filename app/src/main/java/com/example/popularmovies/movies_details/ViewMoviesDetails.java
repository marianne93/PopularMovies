package com.example.popularmovies.movies_details;

import com.example.popularmovies.common.base.ViewBase;
import com.example.popularmovies.common.models.Trailer;

import java.util.List;

/**
 * Created by Marianne.Wazif on 06-Sep-17.
 */

public interface ViewMoviesDetails extends ViewBase {
    void onMoviesTrailersLoadedSucceed(List<Trailer> moviesTrailersList);

    void onMoviesTrailersError(String error);
}
