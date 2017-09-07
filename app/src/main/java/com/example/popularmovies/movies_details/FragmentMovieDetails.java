package com.example.popularmovies.movies_details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.R;
import com.example.popularmovies.common.base.FragmentBase;
import com.example.popularmovies.common.helpers.AppPreferences;
import com.example.popularmovies.common.helpers.Constants;
import com.example.popularmovies.common.helpers.MyApplication;
import com.example.popularmovies.common.helpers.ServicesHelper;
import com.example.popularmovies.common.helpers.Utility;
import com.example.popularmovies.common.models.Movie;
import com.example.popularmovies.common.models.Trailer;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class FragmentMovieDetails extends FragmentBase implements ViewMoviesDetails {

    private OnMovieDetailsFragmentInteractionListener mListener;
    private Movie movie;
    private TextView txtMovieName, txtYear, txtMin, txtRate, txtFav, txtDescription;
    private ImageView imgMovie;
    private Context context;
    private RecyclerView rvTrailers;
    private ProgressWheel progress_wheel;
    private TextView txtErrorMessage;
    private List<Trailer> movieTrailers;
    private MovieTrailerRecyclerViewAdapter movieTrailerRecyclerViewAdapter;
    private PresenterMoviesDetails presenterMoviesDetails;
    private ArrayList<Movie> favoriteMovies;

    public FragmentMovieDetails() {
        // Required empty public constructor
    }

    public static FragmentMovieDetails newInstance(Movie movie) {
        FragmentMovieDetails fragment = new FragmentMovieDetails();
        Bundle args = new Bundle();
        args.putParcelable(Constants.MOVIE_OBJECT_KEY, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(Constants.MOVIE_OBJECT_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        context = getActivity();
        movieTrailers = new ArrayList<>();
        presenterMoviesDetails = new PresenterMoviesDetails(this, context);
        initializeViews(rootView);
        setListeners();
        initTrailerRecyclerViewAdapter();
        setMovieDetails();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenterMoviesDetails.getMoviesTrailer(movie.getId());
    }

    private void initTrailerRecyclerViewAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvTrailers.setLayoutManager(layoutManager);
        movieTrailerRecyclerViewAdapter = new MovieTrailerRecyclerViewAdapter(context, movieTrailers, mListener);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTrailers.getContext(),
                layoutManager.getOrientation());
        rvTrailers.addItemDecoration(dividerItemDecoration);
        rvTrailers.setAdapter(movieTrailerRecyclerViewAdapter);
    }

    private void setMovieDetails() {
        String imageUrl = Constants.IMAGE_BASE_URL + movie.getPosterPath();
        txtMovieName.setText(movie.getOriginalTitle());
        txtYear.setText(Utility.getYear(movie.getReleaseDate()));
        txtRate.setText(String.format(getString(R.string.rate_format), movie.getVoteAverage(), "10"));
        txtDescription.setText(movie.getOverview());
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .fit()
                .into(imgMovie);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieDetailsFragmentInteractionListener) {
            mListener = (OnMovieDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMovieDetailsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected void initializeViews(View v) {
        txtMovieName = (TextView) v.findViewById(R.id.txtMovieName);
        txtDescription = (TextView) v.findViewById(R.id.txtDescription);
        txtFav = (TextView) v.findViewById(R.id.txtFav);
        txtMin = (TextView) v.findViewById(R.id.txtMin);
        txtRate = (TextView) v.findViewById(R.id.txtRate);
        txtYear = (TextView) v.findViewById(R.id.txtYear);
        imgMovie = (ImageView) v.findViewById(R.id.imgMovie);
        rvTrailers = (RecyclerView) v.findViewById(R.id.rvTrailers);
        progress_wheel = (ProgressWheel) v.findViewById(R.id.progressWheel);
        txtErrorMessage = (TextView) v.findViewById(R.id.txtErrorMessage);
    }

    @Override
    protected void setListeners() {
        txtFav.setOnClickListener(txtFavOnClickListener);
    }

    View.OnClickListener txtFavOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            favoriteMovies = Utility.getAllFavoriteMovies(AppPreferences.getString(AppPreferences.KEY_FAVORITES_MOVIES, context, ""));
            if (favoriteMovies.size() != 0 && !Utility.isFavorite(favoriteMovies, movie.getId())) {
                addMovieToFavorites();
                Toast.makeText(context , getString(R.string.movie_is_added),Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(context , getString(R.string.movie_is_existed),Toast.LENGTH_SHORT).show();

        }
    };

    private void addMovieToFavorites() {
        favoriteMovies.add(movie);
        AppPreferences.setString(AppPreferences.KEY_FAVORITES_MOVIES, MyApplication.getmGson().toJson(favoriteMovies), context);
    }

    @Override
    public void showProgress(boolean show) {
        if (show)
            progress_wheel.setVisibility(View.VISIBLE);
        else
            progress_wheel.setVisibility(View.GONE);
    }

    @Override
    public void onMoviesTrailersLoadedSucceed(List<Trailer> moviesTrailersList) {
        this.movieTrailers.addAll(moviesTrailersList);
        if (this.movieTrailers.size() > 0)
            movieTrailerRecyclerViewAdapter.notifyDataSetChanged();
        else {
            txtErrorMessage.setVisibility(View.VISIBLE);
            txtErrorMessage.setText(getString(R.string.no_movies));
        }
    }

    @Override
    public void onMoviesTrailersError(String error) {
        txtErrorMessage.setVisibility(View.VISIBLE);
        txtErrorMessage.setText(error);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMovieDetailsFragmentInteractionListener {
        void onTrailerClicked(String videoID);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ServicesHelper.getInstance(context).getRequestQueue() != null) {
            ServicesHelper.getInstance(context).getRequestQueue().cancelAll(ServicesHelper.Tag.TRAILERS);
        }
    }
}
