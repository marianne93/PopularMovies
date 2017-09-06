package com.example.popularmovies.common.helpers;

import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marianne.Wazif on 27-Aug-17.
 */

public class ServicesHelper {
    private static ServicesHelper mInstance;
    private RequestQueue mRequestQueue;
    private final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private final String APPID_PARAM = "api_key";

    public enum Tag {
        TRAILERS, ALL_MOVIES
    }

    private ServicesHelper(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized ServicesHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ServicesHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public void getMovies(String sortParam, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        String MOVIES_URL = BASE_URL + sortParam;
        MOVIES_URL += new Uri.Builder()
                .appendQueryParameter(APPID_PARAM, Constants.OPEN_POPULAR_MOVIES_API_KEY);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(MOVIES_URL, null, successListener, errorListener);
        jsonObjectRequest.setTag(Tag.ALL_MOVIES);
        //Adding our request to the queue
        addToRequestQueue(jsonObjectRequest);

    }

    public void getMoviesTrailer(int movieID, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        String MOVIES_TRAILER_URL = BASE_URL + String.valueOf(movieID) + "/videos";
        MOVIES_TRAILER_URL += new Uri.Builder()
                .appendQueryParameter(APPID_PARAM, Constants.OPEN_POPULAR_MOVIES_API_KEY);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(MOVIES_TRAILER_URL, null, successListener, errorListener);
        jsonObjectRequest.setTag(Tag.TRAILERS);
        //Adding our request to the queue
        addToRequestQueue(jsonObjectRequest);

    }
}
