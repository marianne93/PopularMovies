package com.example.popularmovies.movies_details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.common.models.Trailer;

import java.util.List;

/**
 * Created by Marianne.Wazif on 06-Sep-17.
 */

public class MovieTrailerRecyclerViewAdapter extends RecyclerView.Adapter<MovieTrailerRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private FragmentMovieDetails.OnMovieDetailsFragmentInteractionListener movieDetailsFragmentInteractionListener;
    private List<Trailer> trailers;
    private ViewHolder viewHolder;

    public MovieTrailerRecyclerViewAdapter(Context context, List<Trailer> trailers, FragmentMovieDetails.OnMovieDetailsFragmentInteractionListener movieDetailsFragmentInteractionListener) {
        this.trailers = trailers;
        this.movieDetailsFragmentInteractionListener = movieDetailsFragmentInteractionListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trailer, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtTrailer.setText(trailers.get(position).getName());
        holder.itemView.setOnClickListener(itemViewOnClickListener);
    }

    private View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (null != movieDetailsFragmentInteractionListener) {
                movieDetailsFragmentInteractionListener.onTrailerClicked(trailers.get(viewHolder.getAdapterPosition()).getKey());
            }
        }
    };

    @Override
    public int getItemCount() {
        return this.trailers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView txtTrailer;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            txtTrailer = (TextView) itemView.findViewById(R.id.txtTrailer);
        }
    }
}
