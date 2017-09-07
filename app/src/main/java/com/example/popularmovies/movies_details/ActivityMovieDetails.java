package com.example.popularmovies.movies_details;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.example.popularmovies.R;
import com.example.popularmovies.common.base.ActivityBase;
import com.example.popularmovies.common.helpers.Constants;
import com.example.popularmovies.common.helpers.Utility;
import com.example.popularmovies.common.models.Movie;

public class ActivityMovieDetails extends ActivityBase implements FragmentMovieDetails.OnMovieDetailsFragmentInteractionListener {
    private Toolbar toolbar;
    private Movie movie;

    public static void startActivity(Context context, Movie movie) {
        Intent i = new Intent(context, ActivityMovieDetails.class);
        i.putExtra(Constants.MOVIE_OBJECT_KEY, movie);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_toolbar_fragment);
        if (getIntent() != null) {
            movie = getIntent().getParcelableExtra(Constants.MOVIE_OBJECT_KEY);
        }
        initializeViews();
        loadFragment();
    }

    @Override
    protected void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getString(R.string.movies_detail), true, R.color.colorPrimary, true);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void loadFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frmFragmentContainer, FragmentMovieDetails.newInstance(movie)).commit();
    }

    @Override
    public void onTrailerClicked(String videoID) {
        Utility.openYouTube(this , videoID);
    }
}
