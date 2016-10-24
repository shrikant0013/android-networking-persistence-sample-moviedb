package com.shrikant.themoviedb.fragments;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.shrikant.themoviedb.R;
import com.shrikant.themoviedb.adapters.RecyclerViewMoviesAdapter;
import com.shrikant.themoviedb.models.Movie;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by spandhare on 10/24/16.
 */

public class SavedMoviesFragment extends Fragment {

    private static final String TAG = "SavedMoviesFragment";
    public ArrayList<Movie> mMovies;
    public RecyclerViewMoviesAdapter mRecyclerViewMoviesAdapter;

    @BindView(R.id.rvMovies)
    RecyclerView mRecyclerViewMovies;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View v = inflater.inflate(R.layout.movies_layout, parent, false);
        ButterKnife.bind(this, v);

        mRecyclerViewMovies.setAdapter(mRecyclerViewMoviesAdapter);
        mRecyclerViewMovies.setHasFixedSize(true);

        // Set layout manager to position the items
        // Attach the layout manager to the recycler view
        mRecyclerViewMovies.setLayoutManager(new LinearLayoutManager(getActivity()));


        mMovies.clear();
        mRecyclerViewMoviesAdapter.notifyDataSetChanged();

        populateSavedMovies();

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovies = new ArrayList<>();
        mRecyclerViewMoviesAdapter =
                new RecyclerViewMoviesAdapter(getActivity(),
                        mMovies);

        mRecyclerViewMoviesAdapter.setMoviePersistsListener(new RecyclerViewMoviesAdapter.MoviePersistsListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Log.i(TAG, "Reached onMovieClick");
                mOnItemSelectedListener.onSavedMovieSelected(movie);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    void populateSavedMovies() {
        List<Movie> movies = SQLite.select().
                from(Movie.class).queryList();

        mMovies.addAll(movies);
        mRecyclerViewMoviesAdapter.notifyDataSetChanged();
    }

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private SavedMoviesFragment.OnItemSelectedListener mOnItemSelectedListener;

    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        void onSavedMovieSelected(Movie movie);
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SavedMoviesFragment.OnItemSelectedListener) {
            mOnItemSelectedListener = (SavedMoviesFragment.OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement SavedMoviesFragment.OnItemSelectedListener");
        }
    }
}
