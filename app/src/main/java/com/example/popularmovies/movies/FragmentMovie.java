package com.example.popularmovies.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularmovies.R;
import com.example.popularmovies.common.base.FragmentBase;
import com.example.popularmovies.common.helpers.Constants;
import com.example.popularmovies.common.models.Result;
import com.pnikosis.materialishprogress.ProgressWheel;

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
    private List<Result> movie;
    private ProgressWheel progress_wheel;
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
        setAdapter();
        return view;
    }

    private void setAdapter() {
        // Set the adapter
        rvMovies.setLayoutManager(new GridLayoutManager(context, Constants.GRID_COLUMNS));
        rvMovies.setAdapter(new MyMovieRecyclerViewAdapter(context, movie, mListener));
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
    public void onError(String error) {

    }

    @Override
    public void onMoviesLoadedSucceed(List<Result> moviesList) {

    }

    @Override
    public void onMoviesError(String error) {

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
