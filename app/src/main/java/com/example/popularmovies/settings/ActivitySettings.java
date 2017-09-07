package com.example.popularmovies.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.example.popularmovies.R;
import com.example.popularmovies.common.base.ActivityBase;

public class ActivitySettings extends ActivityBase implements FragmentSettings.OnSettingsFragmentInteractionListener {
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_toolbar_fragment);
        initializeViews();
        loadFragment();
    }
    public static Intent createSettingsActivityIntent(Context context) {
        return new Intent(context, ActivitySettings.class);
    }
    @Override
    protected void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getString(R.string.action_settings), true, R.color.colorPrimary, true);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void loadFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frmFragmentContainer, FragmentSettings.newInstance()).commit();

    }

    @Override
    public void onSortTypeChanged() {
        setResult(Activity.RESULT_OK);
    }

}
