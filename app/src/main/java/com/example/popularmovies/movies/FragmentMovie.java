package com.example.popularmovies.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.common.base.FragmentBase;
import com.example.popularmovies.common.helpers.Constants;
import com.example.popularmovies.common.models.Result;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FragmentMovie extends FragmentBase implements ViewMovies {
    private OnListFragmentInteractionListener mListener;
    private RecyclerView rvMovies;
    private Context context;
    private List<Result> moviesList;
    private ProgressWheel progress_wheel;
    private TextView txtErrorMessage;
    private PresenterMovies presenterMovies;
    private MyMovieRecyclerViewAdapter moviesAdapter;
    private String sortParam;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentMovie() {
    }

    public static FragmentMovie newInstance() {
        return new FragmentMovie();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        context = getActivity();
        initializeViews(view);
        presenterMovies = new PresenterMovies(context, this);
        moviesList = new ArrayList<>();
        initRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sortParam = "popular";
        presenterMovies.getMovies(sortParam);
    }

    private void initRecyclerView() {
        // Set the adapter
        rvMovies.setLayoutManager(new GridLayoutManager(context, Constants.GRID_COLUMNS));
        moviesAdapter = new MyMovieRecyclerViewAdapter(context, moviesList, mListener);
        rvMovies.setAdapter(moviesAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected void initializeViews(View v) {
        rvMovies = (RecyclerView) v.findViewById(R.id.rvMovies);
        progress_wheel = (ProgressWheel) v.findViewById(R.id.progressWheel);
        txtErrorMessage = (TextView) v.findViewById(R.id.txtErrorMessage);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void showProgress(boolean show) {
        if (show)
            progress_wheel.setVisibility(View.VISIBLE);
        else
            progress_wheel.setVisibility(View.GONE);
    }

    @Override
    public void onMoviesLoadedSucceed(List<Result> moviesList) {
        this.moviesList.addAll(moviesList);
        if (this.moviesList.size() > 0)
            moviesAdapter.notifyDataSetChanged();
        else {
            txtErrorMessage.setVisibility(View.VISIBLE);
            txtErrorMessage.setText(getString(R.string.no_movies));
        }
    }

    @Override
    public void onMoviesError(String error) {
        txtErrorMessage.setVisibility(View.VISIBLE);
        txtErrorMessage.setText(error);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(int position);
    }
}
