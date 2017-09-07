package com.example.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Marianne on 08-Aug-16.
 */

public class TrailersAdapter extends BaseAdapter {
    private Context mcontext;

    public TrailersAdapter(Context context) {
        mcontext = context;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mcontext).inflate(R.layout.list_trailer_item, viewGroup, false);
        }
        TextView textview = (TextView) view.findViewById(R.id.list_item_trailer_textview);
        if (MoviesDetailsFragments.movieTrailers.size() != 0) {
            textview.setText(MoviesDetailsFragments.movieTrailers.get(i).name);
        }


        return view;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return MoviesDetailsFragments.movieTrailers.get(i);
    }

    @Override
    public int getCount() {
        return MoviesDetailsFragments.movieTrailers.size();
    }
}
