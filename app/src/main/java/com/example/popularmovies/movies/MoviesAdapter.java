package com.example.popularmovies.movies;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.popularmovies.MoviesFragment;
import com.example.popularmovies.MySingleton;
import com.example.popularmovies.R;

import java.util.ArrayList;

/**
 * Created by Marianne on 01-Jul-16.
 */
public class MoviesAdapter extends BaseAdapter {
    //Imageloader to load images
    private ImageLoader mimageLoader;

    //Context
    private Context mcontext;



    public MoviesAdapter(Context context) {
        mcontext = context;

    }

    @Override
    public int getCount() {
        return MoviesFragment.movies.size();
    }

    @Override
    public Object getItem(int i) {
        return MoviesFragment.movies.get(i).poster_url;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        mimageLoader = MySingleton.getInstance(mcontext).getImageLoader();


        if (view == null) {
            view = LayoutInflater.from(mcontext).inflate(R.layout.grid_image_item, viewGroup, false);
        }
        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.imageView);
        if (MoviesFragment.movies.size() != 0) {

            imageView.setImageUrl(MoviesFragment.movies.get(position).poster_url, mimageLoader);
        }
        return view;


    }
}
