package com.shrikant.themoviedb.fragments;

import com.shrikant.themoviedb.R;
import com.shrikant.themoviedb.adapters.RecyclerViewMoviesAdapter;
import com.shrikant.themoviedb.models.Movie;
import com.shrikant.themoviedb.network.RetrofitExample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by spandhare on 10/23/16.
 */

public class TopRatedMoviesFragment extends Fragment {

    private static final String TAG = "TopRatedMoviesFragment";
    public ArrayList<Movie> mMovies;
    public RecyclerViewMoviesAdapter mRecyclerViewMoviesAdapter;

    @BindView(R.id.rvMovies)
    RecyclerView mRecyclerViewMovies;

    @BindView(R.id.avi)
    com.wang.avi.AVLoadingIndicatorView mLoadingIndicatorView;

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

        if (!isOnline()) {
            Toast.makeText(getContext(), "Your device is not online, " +
                            "check wifi and try again!",
                    Toast.LENGTH_LONG).show();
        } else {
            mMovies.clear();
            mRecyclerViewMoviesAdapter.notifyDataSetChanged();

            //kick off network query
            populateTopRatedMovies();
        }
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovies = new ArrayList<>();
        mRecyclerViewMoviesAdapter =
                new RecyclerViewMoviesAdapter(getActivity(),
                        mMovies);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (InterruptedException | IOException e) { e.printStackTrace(); }
        return false;
    }

    void populateTopRatedMovies() {
        RetrofitExample
                .with(getContext())
                .load(mMovies)
                .into(mRecyclerViewMoviesAdapter)
                .placeholder(mLoadingIndicatorView)
                .updateTopRatedMovies();
    }
}