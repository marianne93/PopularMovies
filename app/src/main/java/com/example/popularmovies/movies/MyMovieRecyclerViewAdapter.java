package com.example.popularmovies.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmovies.R;
import com.example.popularmovies.common.helpers.Constants;
import com.example.popularmovies.common.models.Movie;
import com.example.popularmovies.movies.FragmentMovie.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> {

    private List<Movie> movies;
    private OnListFragmentInteractionListener listFragmentInteractionListener;
    private Context context;

    public MyMovieRecyclerViewAdapter(Context context, List<Movie> movies, OnListFragmentInteractionListener listFragmentInteractionListener) {
        this.movies = movies;
        this.listFragmentInteractionListener = listFragmentInteractionListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String url = Constants.IMAGE_BASE_URL + movies.get(position).getPosterPath();
        Picasso.with(holder.imgMovie.getContext())
                .load(url)
                .placeholder(R.drawable.placeholder)
                .fit()
                .into(holder.imgMovie);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listFragmentInteractionListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listFragmentInteractionListener.onListFragmentInteraction(movies.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private ImageView imgMovie;

        ViewHolder(View view) {
            super(view);
            mView = view;
            imgMovie = (ImageView) view.findViewById(R.id.imgMovie);
        }
    }
}
