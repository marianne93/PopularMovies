package com.example.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.popularmovies.common.helpers.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Marianne on 08-Jul-16.
 */
public class MoviesDetailsFragments extends Fragment  {
    public static String LOG_TAG = MoviesDetailsFragments.class.getSimpleName();
    static final String DETAIL_PARAM = "position";
    private int mposition;
    private TextView mname;
    private NetworkImageView mimageview;
    private TextView myear;
    private TextView mduration;
    private TextView mrate;
    private TextView mOverview;
    private ImageLoader mimageLoader;
    private ListView mlistView;
    private Button mFavBtn;
    private TrailersAdapter trailersAdapter;
    public static ArrayList<Trailer> movieTrailers = new ArrayList<Trailer>();
    public static ArrayList<Movie> FavoriteMovies = new ArrayList<Movie>();

    public MoviesDetailsFragments() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mposition = arguments.getInt(MoviesDetailsFragments.DETAIL_PARAM);

        } else {
            mposition = 0;
        }
        //Toast.makeText(getActivity(),String.valueOf(mposition) ,Toast.LENGTH_LONG).show();
        View rootView = inflater.inflate(R.layout.moviesdetails_fragment, container, false);
        mname = (TextView) rootView.findViewById(R.id.txtMovieName);
        mimageview = (NetworkImageView) rootView.findViewById(R.id.imgMovie);
        myear = (TextView) rootView.findViewById(R.id.txtYear);
         mOverview = (TextView) rootView.findViewById(R.id.txtDescription);
        mrate = (TextView) rootView.findViewById(R.id.txtRate);
        mlistView = (NonScrollListView) rootView.findViewById(R.id.listview_trailers);
        mFavBtn = (Button) rootView.findViewById(R.id.txtFav);

         trailersAdapter = new TrailersAdapter(getActivity());
        mlistView.setAdapter(trailersAdapter);

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + movieTrailers.get(position).key));
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + movieTrailers.get(position).key));
                    startActivity(intent);
                }


            }
        });

        mFavBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Movie movie = MoviesFragment.movies.get(mposition);
                Boolean found = Utility.containsMovie(FavoriteMovies , movie.id);
                if (!found)
                {
                    FavoriteMovies.add(movie);
                    Toast.makeText(getActivity() , "Added to Favorites", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getActivity() , "This movie is existed", Toast.LENGTH_LONG).show();


            }
        });



        if (MoviesFragment.movies.size() != 0) {
            Movie movie = MoviesFragment.movies.get(mposition);
            FetchTrailers(movie.id);

            mname.setText(movie.display_name);
            mimageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
            mimageview.setImageUrl(movie.poster_url, mimageLoader);
            mOverview.setText(movie.overview);
            myear.setText(Utility.getYear(movie.released_date));
            mrate.setText(Utility.getRate(movie.rating) + "/10");
        }


        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();

    }
    private void FetchTrailers(int idParam)
    {
        final String MOVIES_BASE_URL =
                "http://api.themoviedb.org/3/movie/"+ idParam + "/videos?";
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
                        getTrailersDataFromJson(response);

                        if ( trailersAdapter != null) {
                            trailersAdapter.notifyDataSetChanged();
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
    private void getTrailersDataFromJson(JSONObject response) {
        final String OWM_LIST = "results";
        final String KEY = "key";
        movieTrailers = new ArrayList<>();

        try {

            JSONArray trailersArray = response.getJSONArray(OWM_LIST);
            for (int i = 0; i < trailersArray.length(); i++) {
                JSONObject trailerObj = trailersArray.getJSONObject(i);
                Trailer trailer = new Trailer();
                trailer.key = trailerObj.getString(KEY);
                int trailerCount = i+1;
                trailer.name = "Trailer ".concat(String.valueOf(trailerCount));

                movieTrailers.add(trailer);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
