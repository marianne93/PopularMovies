package com.example.popularmovies.movies;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.popularmovies.R;
import com.example.popularmovies.common.base.ActivityBase;
import com.example.popularmovies.common.helpers.Utility;
import com.example.popularmovies.common.models.Movie;
import com.example.popularmovies.movies_details.ActivityMovieDetails;
import com.example.popularmovies.movies_details.FragmentMovieDetails;
import com.example.popularmovies.settings.ActivitySettings;

public class ActivityMovies extends ActivityBase implements FragmentMovie.OnListFragmentInteractionListener,FragmentMovieDetails.OnMovieDetailsFragmentInteractionListener {
    private Toolbar toolbar;
    private static final int SETTINGS_REQUEST_CODE = 2;
    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_toolbar_fragment);
        initializeViews();
        mTwoPane = findViewById(R.id.frmMoviesDetailsFragmentContainer) != null;
        if (savedInstanceState == null) {
            loadFragment();
        }

    }

    @Override
    protected void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getString(R.string.popular_movies), false, R.color.colorPrimary, true);
    }

    @Override
    protected void setListeners() {

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
            startActivityForResult(ActivitySettings.createSettingsActivityIntent(this), SETTINGS_REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void loadFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frmFragmentContainer, FragmentMovie.newInstance()).commit();
    }

    @Override
    public void onListFragmentInteraction(Movie movie) {
        if (!mTwoPane) {
            ActivityMovieDetails.startActivity(this, movie);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frmMoviesDetailsFragmentContainer, FragmentMovieDetails.newInstance(movie)).commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SETTINGS_REQUEST_CODE) {
                loadFragment();
            }
        }
    }

    @Override
    public void onTrailerClicked(String videoID) {
        Utility.openYouTube(this , videoID);
    }
}
