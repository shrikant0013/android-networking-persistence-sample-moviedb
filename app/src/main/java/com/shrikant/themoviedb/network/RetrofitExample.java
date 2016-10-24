package com.shrikant.themoviedb.network;

import com.shrikant.themoviedb.R;
import com.shrikant.themoviedb.adapters.ComplexRecyclerViewCurrentMoviesAdapter;
import com.shrikant.themoviedb.models.Movie;
import com.shrikant.themoviedb.models.MovieList;
import com.shrikant.themoviedb.network.retro.TheMovieDBService;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by spandhare on 10/23/16.
 */

public class RetrofitExample {
    private ComplexRecyclerViewCurrentMoviesAdapter mComplexRecyclerViewCurrentMoviesAdapter;
    private ArrayList<Movie> mMovies;
    private Context mContext;

    private static RetrofitExample singletonInstance;

    public RetrofitExample(
            ComplexRecyclerViewCurrentMoviesAdapter complexRecyclerViewCurrentMoviesAdapter,
            ArrayList<Movie> movies,
            Context context) {
        mComplexRecyclerViewCurrentMoviesAdapter = complexRecyclerViewCurrentMoviesAdapter;
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

    public RetrofitExample into(ComplexRecyclerViewCurrentMoviesAdapter complexRecyclerViewCurrentMoviesAdapter) {
        mComplexRecyclerViewCurrentMoviesAdapter = complexRecyclerViewCurrentMoviesAdapter;
        return singletonInstance;
    }

    public void updateTopRatedMovies() {
        Log.i("Retroexample", "Ready to update movies");
        final Call<MovieList> call =
                TheMovieDBService.topMovies.fetchTopRated(mContext.getString(R.string.api_key));

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                Log.i("Retroexample", call.request().url().toString());
                int statusCode = response.code();

                MovieList fetchedMovies = response.body();
                Log.i("SearchActivity", fetchedMovies.getResults().size() + " movies found");
                mMovies.clear();
                mMovies.addAll(fetchedMovies.getResults());

                Log.i("SearchActivity", mMovies.size() + " movies found");

                mComplexRecyclerViewCurrentMoviesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                // Log error here since request failed
                Log.i("Retroexample", call.request().url().toString());
                Log.i("Retroexample", "failure:" + t.getMessage());
            }
        });
    }
}
