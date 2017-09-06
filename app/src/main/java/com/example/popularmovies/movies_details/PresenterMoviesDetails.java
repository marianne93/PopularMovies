package com.example.popularmovies.movies_details;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.popularmovies.R;
import com.example.popularmovies.common.base.PresenterBase;
import com.example.popularmovies.common.helpers.Parser;
import com.example.popularmovies.common.helpers.ServicesHelper;
import com.example.popularmovies.common.helpers.VolleyErrorHandler;
import com.example.popularmovies.common.models.dto.MoviesTrailersServiceResponse;

import org.json.JSONObject;

/**
 * Created by Marianne.Wazif on 06-Sep-17.
 */

public class PresenterMoviesDetails extends PresenterBase {
    private ViewMoviesDetails viewMoviesDetails;
    private Context context;

    public PresenterMoviesDetails(ViewMoviesDetails viewMoviesDetails, Context context) {
        this.viewMoviesDetails = viewMoviesDetails;
        this.context = context;
    }

    public void getMoviesTrailer(int movieID) {
        viewMoviesDetails.showProgress(true);
        ServicesHelper.getInstance(context).getMoviesTrailer(movieID, successListener, errorListener);
    }

    private Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            viewMoviesDetails.showProgress(false);
            if (response != null) {
                MoviesTrailersServiceResponse moviesTrailersResponse = Parser.getInstance().getMoviesTrailersResponse(response.toString());
                if (moviesTrailersResponse != null) {
                    viewMoviesDetails.onMoviesTrailersLoadedSucceed(moviesTrailersResponse.getResults());
                } else
                    viewMoviesDetails.onMoviesTrailersError(context.getString(R.string.somethingWrong));
            } else
                viewMoviesDetails.onMoviesTrailersError(context.getString(R.string.somethingWrong));
        }
    };
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            viewMoviesDetails.onMoviesTrailersError(VolleyErrorHandler.getErrorMessage(context, error));
            viewMoviesDetails.showProgress(false);
        }
    };
}
