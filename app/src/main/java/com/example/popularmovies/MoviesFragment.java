package com.example.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.popularmovies.movies.MoviesAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment {

    public static String LOG_TAG = MoviesFragment.class.getSimpleName();
    private String msort;

    private GridView mGridView;
    private int mPosition = GridView.INVALID_POSITION;

    private static final String SELECTED_KEY = "selected_position";
    private Bundle msavedInstanceState;
    MoviesAdapter moviesAdapter;
    public static ArrayList<Movie> movies = new ArrayList<Movie>();
   // public static ArrayList<String> images = new ArrayList<String>();

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        if (MainActivity.twoPane) {
            mGridView.setNumColumns(3);
        }
        String sortParam = Utility.getPreferredSort(getActivity());
        if (sortParam.equals("favorites")) {

            getFavorites();

        } else {
            getData(sortParam);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movies_fragment, container, false);
        mGridView = (GridView) rootView.findViewById(R.id.gridView);


        String sortParam = Utility.getPreferredSort(getActivity());
        moviesAdapter = new MoviesAdapter(getActivity());
        mGridView.setAdapter(moviesAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ((CallBack) getActivity()).onItemSelected(position);
                mPosition = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (mPosition != GridView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);

        }
        super.onSaveInstanceState(outState);
    }



    private void getFavorites() {

        movies = new ArrayList<>();
        movies = getMovies(getActivity());
        MoviesDetailsFragments.FavoriteMovies = new ArrayList<>();
        MoviesDetailsFragments.FavoriteMovies = getMovies(getActivity());
        moviesAdapter.notifyDataSetChanged();
        if (mPosition != GridView.INVALID_POSITION && MainActivity.twoPane) {
            ((CallBack) getActivity()).onItemSelected(mPosition);
            mGridView.smoothScrollToPosition(mPosition);
        } else if (mPosition == GridView.INVALID_POSITION && MainActivity.twoPane) {
            ((CallBack) getActivity()).onItemSelected(0);


        }


    }

    public ArrayList<Movie> getMovies(Context context) {
        SharedPreferences settings;
        List<Movie> movies;

        settings = context.getSharedPreferences("FAVORITES",
                Context.MODE_PRIVATE);

        if (settings.contains("MOVIES")) {
            String jsonMovies = settings.getString("MOVIES", null);
            Gson gson = new Gson();
            Movie[] moviesItems = gson.fromJson(jsonMovies,
                    Movie[].class);

            movies = Arrays.asList(moviesItems);
            movies = new ArrayList<Movie>(movies);
        } else
            return null;

        return (ArrayList<Movie>) movies;
    }

    private void getData(String sortParam) {

        movies = new ArrayList<>();
      //  images = new ArrayList<>();
        final String MOVIES_BASE_URL =
                "http://api.themoviedb.org/3/movie/" + sortParam + "?";
        final String APPID_PARAM = "api_key";
        final String IMAGE = "poster_path";


        Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_POPULAR_MOVIES_API_KEY)
                .build();
        final String DATA_URL = builtUri.toString();
        Log.d(LOG_TAG, DATA_URL);
        //Creating a json array request to get the json from our api
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(DATA_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Displaying our grid
                        getMoviesDataFromJson(response);

                        if (moviesAdapter != null) {
                            moviesAdapter.notifyDataSetChanged();
                            if (mPosition != GridView.INVALID_POSITION && MainActivity.twoPane) {
                                ((CallBack) getActivity()).onItemSelected(mPosition);
                               mGridView.smoothScrollToPosition(mPosition);
                                mGridView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mGridView.smoothScrollToPosition(mPosition);
                                        mGridView.setSelection(mPosition);
                                    }
                                });
                            } else if (mPosition == GridView.INVALID_POSITION && MainActivity.twoPane) {
                                ((CallBack) getActivity()).onItemSelected(0);


                            }


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        //Adding our request to the queue
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void getMoviesDataFromJson(JSONObject response) {
        final String OWM_LIST = "results";
        final String OWM_IMAGE = "poster_path";
        final String OWM_OVERVIEW = "overview";
        final String OWM_DATE = "release_date";
        final String OWM_TITLE = "original_title";
        final String OWM_RATE = "vote_average";
        final String OWM_ID = "id";


        try {
            JSONArray movieArray = response.getJSONArray(OWM_LIST);
            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movieObj = movieArray.getJSONObject(i);
                Movie movie = new Movie();
                movie.id = movieObj.getInt(OWM_ID);
                movie.display_name = movieObj.getString(OWM_TITLE);
                movie.overview = movieObj.getString(OWM_OVERVIEW);
                movie.poster_url = "http://image.tmdb.org/t/p/w185/" + movieObj.getString(OWM_IMAGE);
                movie.released_date = movieObj.getString(OWM_DATE);
                movie.rating = (float) movieObj.getDouble(OWM_RATE);

                movies.add(movie);
                Log.d(LOG_TAG, movie.poster_url);

               // images.add(movie.poster_url);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public interface CallBack {
        /**
         * A callback interface that all activities containing this fragment must
         * implement. This mechanism allows activities to be notified of item
         * selections
         */
        public void onItemSelected(int position);
    }

    public void onsortChanged(String newSort) {

        mPosition = 0;

        if (newSort.equals("favorites")) {
            getFavorites();
        } else {
            getData(newSort);
        }


    }

}
