package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;

import com.android.volley.RequestQueue;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MoviesFragment.CallBack {
    private final String MOVIESFRAGMENT_TAG = "MFTAG";
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private String msort;
    private static android.support.v4.app.Fragment mContent;
    static boolean twoPane;
    private int mposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        msort = Utility.getPreferredSort(this);

        super.onCreate(savedInstanceState);
        RequestQueue mRequestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        // Instantiate the cache
        //   Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
//        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        //      mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        //    mRequestQueue.start();
        setContentView(R.layout.activity_main);


        if (findViewById(R.id.fragment_movie) != null) {

            twoPane = true;

            if (savedInstanceState == null) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MoviesDetailsFragments(), DETAILFRAGMENT_TAG).commit();

            }

        } else {
            twoPane = false;
            if (savedInstanceState == null) {

                MoviesFragment fragment = new MoviesFragment();

                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, MOVIESFRAGMENT_TAG).commit();
            }

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        save_Movie_To_Shared_Prefs(this , MoviesDetailsFragments.FavoriteMovies);


    }
    public void save_Movie_To_Shared_Prefs(Context context, ArrayList<Movie> movies) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences("FAVORITES",
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonMovies = gson.toJson(movies);

        editor.putString("MOVIES", jsonMovies);

        editor.commit();
    }
    @Override
    public void onItemSelected(int position) {

        MoviesDetailsFragments fragment = new MoviesDetailsFragments();
        Bundle args = new Bundle();
        args.putInt(MoviesDetailsFragments.DETAIL_PARAM, position);
        fragment.setArguments(args);
        mposition = position;
        if (twoPane) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, DETAILFRAGMENT_TAG).commit();
        } else {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, DETAILFRAGMENT_TAG).addToBackStack(null).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String sort = Utility.getPreferredSort(this);
        if (sort != null && !sort.equals(msort)) {
            MoviesFragment mf;

            if (findViewById(R.id.fragment_movie) != null) {
                mf = (MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_movie);

            } else {
                mf = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(MOVIESFRAGMENT_TAG);

            }
            mf.movies = new ArrayList<>();
          //  mf.images = new ArrayList<>();


            if (null != mf) {
                mf.onsortChanged(sort);
            }
            //      MoviesDetailsFragments df = (MoviesDetailsFragments)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
            //      if ( null != df ) {
            //          df.onsortChanged(sort);
            //      }

            msort = sort;
        }


    }

}
