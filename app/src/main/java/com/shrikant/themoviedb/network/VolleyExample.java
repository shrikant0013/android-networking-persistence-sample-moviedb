package com.shrikant.themoviedb.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shrikant.themoviedb.R;
import com.shrikant.themoviedb.adapters.RecyclerViewMoviesAdapter;
import com.shrikant.themoviedb.models.Movie;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spandhare on 10/24/16.
 */

public class VolleyExample {

    private static final String TAG = "RetrofitExample";
    private final static String API_KEY = "api_key";
    private final static String LANGUAGE = "language";
    private String URL = "https://api.themoviedb.org/3/movie/upcoming?language=en-US&api_key=";


    private RecyclerViewMoviesAdapter mRecyclerViewMoviesAdapter;
    private ArrayList<Movie> mMovies;
    private Context mContext;
    private com.wang.avi.AVLoadingIndicatorView mLoadingIndicatorView;

    public VolleyExample(RecyclerViewMoviesAdapter recyclerViewMoviesAdapter,
                         ArrayList<Movie> movies,
                         com.wang.avi.AVLoadingIndicatorView loadingIndicatorView,
                         Context context) {
        mRecyclerViewMoviesAdapter = recyclerViewMoviesAdapter;
        mMovies = movies;
        mContext = context;
        mLoadingIndicatorView = loadingIndicatorView;

        URL = URL +  mContext.getString(R.string.api_key);
    }

    public void updateUpcomingMovies() {

        //Show loading animation
        mLoadingIndicatorView.setVisibility(View.VISIBLE);
        mLoadingIndicatorView.show();

        RequestQueue mRequestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response != null) {
                                Gson gson = new GsonBuilder().create();
                                JsonObject jsonObject = gson.fromJson(response.toString(),
                                        JsonObject.class);
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
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
		/* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }
}
