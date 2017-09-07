package com.example.popularmovies.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.popularmovies.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSettings extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    private OnSettingsFragmentInteractionListener mListener;

    public static FragmentSettings newInstance() {
        return new FragmentSettings();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add the shared preference change listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSettingsFragmentInteractionListener) {
            mListener = (OnSettingsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSettingsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null && preference instanceof ListPreference) {
            ListPreference listPref = (ListPreference) preference;
            preference.setSummary(listPref.getEntry());
            mListener.onSortTypeChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove the shared preference change listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public interface OnSettingsFragmentInteractionListener {
        void onSortTypeChanged();
    }
}
