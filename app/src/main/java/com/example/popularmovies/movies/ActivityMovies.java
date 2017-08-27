package com.example.popularmovies.movies;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.popularmovies.R;
import com.example.popularmovies.common.base.ActivityBase;
import com.example.popularmovies.common.models.Result;

public class ActivityMovies extends ActivityBase implements FragmentMovie.OnListFragmentInteractionListener {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_toolbar_fragment);
    }

    @Override
    protected void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void loadFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frmFragmentContainer, FragmentMovie.newInstance()).commit();
    }

    @Override
    public void onListFragmentInteraction(int position) {

    }
}
