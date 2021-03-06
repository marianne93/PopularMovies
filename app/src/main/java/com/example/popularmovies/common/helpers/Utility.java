package com.example.popularmovies.common.helpers;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.renderscript.Sampler;

import com.example.popularmovies.Movie;
import com.example.popularmovies.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Marianne on 09-Jul-16.
 */
public class Utility {
    public static String getPreferredSort(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_sorts_key),
                context.getString(R.string.pref_sorts_popular));

    }

    public static String getYear(String date) {
        String year = "";
        if (date != null && date.length() > 0 && date.indexOf("-") > 0) {
            year = date.substring(0, date.indexOf("-"));
        } else {
            year = date;
        }
        return year;
    }

    public static String getRate(float averageRate) {
        double rate = Double.parseDouble(String.valueOf(averageRate));
        return String.valueOf(rate);

    }
    public static boolean isFavorite(ArrayList<com.example.popularmovies.common.models.Movie> movies, int id) {
        for(com.example.popularmovies.common.models.Movie movie : movies) {
            if( movie.getId() == id) {
                return true;
            }
        }
        return false;
    }
    public static ArrayList<com.example.popularmovies.common.models.Movie> getAllFavoriteMovies(String favoriteMovies) {
       return Parser.getInstance().getFavoriteMovies(favoriteMovies);
    }
    public static void openYouTube(Context context , String videoID){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoID));
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + videoID));
            context.startActivity(intent);
        }
    }
}
