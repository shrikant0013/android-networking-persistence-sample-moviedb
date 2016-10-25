package com.shrikant.themoviedb.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.shrikant.themoviedb.R;
import com.shrikant.themoviedb.adapters.RecyclerViewMoviesAdapter;
import com.shrikant.themoviedb.models.Movie;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by spandhare on 10/23/16.
 */

public class AsyncHTTPClientExample {

    private static final String TAG = "AsyncHTTPClientExample";
    private final static String API_KEY = "api_key";
    private final static String LANGUAGE = "language";
    private String URL = "https://api.themoviedb.org/3/movie/now_playing";
    private RecyclerViewMoviesAdapter mRecyclerViewMoviesAdapter;
    private ArrayList<Movie> mMovies;
    private Context mContext;
    private com.wang.avi.AVLoadingIndicatorView mLoadingIndicatorView;

    public AsyncHTTPClientExample(
            RecyclerViewMoviesAdapter recyclerViewMoviesAdapter,
            ArrayList<Movie> movies,
            com.wang.avi.AVLoadingIndicatorView loadingIndicatorView,
            Context context) {
        mRecyclerViewMoviesAdapter = recyclerViewMoviesAdapter;
        mMovies = movies;
        mContext = context;
        mLoadingIndicatorView = loadingIndicatorView;
    }

    public ArrayList<Movie> updateNowPlayingMovies() {

        //Show loading animation
        mLoadingIndicatorView.setVisibility(View.VISIBLE);
        mLoadingIndicatorView.show();

        Log.i(TAG,"Getting Now playing");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams requestParams = constructQueryRequestParams();

        asyncHttpClient.get(URL, requestParams, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                try {
                    if (response != null) {
                        Gson gson = new GsonBuilder().create();
                        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                        if (jsonObject.has("results")) {
                            JsonArray jsonResultsArray = jsonObject.getAsJsonArray("results");
                            if (jsonResultsArray != null) {
                                Type collectionType = new TypeToken<List<Movie>>() {
                                }.getType();

                                ArrayList<Movie> fetchedMovies = gson.fromJson(jsonResultsArray,
                                        collectionType);
                                mMovies.clear();
                                mMovies.addAll(fetchedMovies);

                                Log.i(TAG, mMovies.size() + " movies found");
                            }
                        }
                    }
                } catch (JsonSyntaxException e) {
                    Log.w(TAG, "Exception while parsing json " + e.getMessage());
                    Toast.makeText(mContext, "Opps looks like " +
                                    "some problem, try again",
                            Toast.LENGTH_SHORT).show();
                }

                //Hide loading animation
                mLoadingIndicatorView.setVisibility(View.INVISIBLE);
                mLoadingIndicatorView.hide();

                mRecyclerViewMoviesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response,
                                  Throwable throwable) {
                Log.w(TAG, "HTTP Request failure: " + statusCode + " " +
                        throwable.getMessage());
            }
        });

        return null;
    }

    public RequestParams constructQueryRequestParams() {

        RequestParams requestParams = new RequestParams();
        requestParams.put(API_KEY,  mContext.getString(R.string.api_key));
        requestParams.put(LANGUAGE, "en-US");

        return requestParams;
    }
}
