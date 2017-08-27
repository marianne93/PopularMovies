package com.example.popularmovies.movies;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.popularmovies.R;
import com.example.popularmovies.common.base.PresenterBase;
import com.example.popularmovies.common.helpers.Parser;
import com.example.popularmovies.common.helpers.VolleyErrorHandler;
import com.example.popularmovies.common.models.dto.MoviesServiceResponse;

import org.json.JSONObject;

/**
 * Created by Marianne.Wazif on 27-Aug-17.
 */

public class PresenterMovies extends PresenterBase {
    private ViewMovies viewMovies;
    private Context context;

    public PresenterMovies(ViewMovies viewMovies) {
        this.viewMovies = viewMovies;
    }

    public void getMovies(String sortParam) {
        viewMovies.showProgress(true);
    }

    private Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            viewMovies.showProgress(false);
            if (response != null) {
                MoviesServiceResponse moviesResponse = Parser.getInstance().getMoviesResponse(response.toString());
                if (moviesResponse != null) {
                    viewMovies.onMoviesLoadedSucceed(moviesResponse.getResults());
                } else
                    viewMovies.onMoviesError(context.getString(R.string.somethingWrong));
            } else
                viewMovies.onMoviesError(context.getString(R.string.somethingWrong));
        }
    };
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            viewMovies.onMoviesError(VolleyErrorHandler.getErrorMessage(context, error));
            viewMovies.showProgress(false);
        }
    };
}
