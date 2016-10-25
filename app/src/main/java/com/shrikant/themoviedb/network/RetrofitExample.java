package com.shrikant.themoviedb.network;

import com.shrikant.themoviedb.R;
import com.shrikant.themoviedb.adapters.RecyclerViewMoviesAdapter;
import com.shrikant.themoviedb.models.Movie;
import com.shrikant.themoviedb.models.MovieList;
import com.shrikant.themoviedb.network.retro.TheMovieDBService;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by spandhare on 10/23/16.
 */

public class RetrofitExample {

    private static final String TAG = "RetrofitExample";
    private RecyclerViewMoviesAdapter mRecyclerViewMoviesAdapter;
    private ArrayList<Movie> mMovies;
    private Context mContext;
    private com.wang.avi.AVLoadingIndicatorView mLoadingIndicatorView;

    private static RetrofitExample singletonInstance;

    public RetrofitExample(
            RecyclerViewMoviesAdapter recyclerViewMoviesAdapter,
            ArrayList<Movie> movies,
            Context context) {
        mRecyclerViewMoviesAdapter = recyclerViewMoviesAdapter;
        mMovies = movies;
        mContext = context;
    }

    private RetrofitExample() {

    }
    public static RetrofitExample with(Context context) {
        RetrofitExample obj = RetrofitExample.getInstance();
        obj.mContext = context;

        return obj;
    }

    private static RetrofitExample getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new RetrofitExample();
        }
        return singletonInstance;
    }

    public RetrofitExample load(ArrayList<Movie> movies) {
        mMovies = movies;
        return singletonInstance;
    }

    public RetrofitExample into(RecyclerViewMoviesAdapter recyclerViewMoviesAdapter) {
        mRecyclerViewMoviesAdapter = recyclerViewMoviesAdapter;
        return singletonInstance;
    }

    public RetrofitExample placeholder(com.wang.avi.AVLoadingIndicatorView loadingIndicatorView) {
        mLoadingIndicatorView = loadingIndicatorView;
        return singletonInstance;
    }

    public void updateTopRatedMovies() {

        //Startloading Animation
        mLoadingIndicatorView.setVisibility(View.VISIBLE);
        mLoadingIndicatorView.show();

        Log.i(TAG, "Ready for Movies!");
        final Call<MovieList> call =
                TheMovieDBService.topMovies.fetchTopRated(mContext.getString(R.string.api_key));

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                Log.i(TAG, call.request().url().toString());
                Log.i(TAG, "Response Status code: " + response.code());

                MovieList fetchedMovies = response.body();
                mMovies.clear();
                mMovies.addAll(fetchedMovies.getResults());

                Log.i(TAG, mMovies.size() + " movies found");

                //Hide loading animation
                mLoadingIndicatorView.setVisibility(View.INVISIBLE);
                mLoadingIndicatorView.hide();

                mRecyclerViewMoviesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                // Log error here since request failed
                Log.i(TAG, call.request().url().toString());
                Log.i(TAG, t.getMessage());
            }
        });
    }
}
