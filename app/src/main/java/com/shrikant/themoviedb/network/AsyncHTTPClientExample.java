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
import com.shrikant.themoviedb.adapters.ComplexRecyclerViewCurrentMoviesAdapter;
import com.shrikant.themoviedb.models.Movie;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by spandhare on 10/23/16.
 */

public class AsyncHTTPClientExample implements NetworkClient{

    private final static String API_KEY = "api_key";
    private final static String LANGUAGE = "language";
    private String URL = "https://api.themoviedb.org/3/movie/now_playing";
    private ComplexRecyclerViewCurrentMoviesAdapter mComplexRecyclerViewCurrentMoviesAdapter;
    private ArrayList<Movie> mMovies;
    private Context mContext;

    public AsyncHTTPClientExample(
            ComplexRecyclerViewCurrentMoviesAdapter complexRecyclerViewCurrentMoviesAdapter,
            ArrayList<Movie> movies,
            Context context) {
        mComplexRecyclerViewCurrentMoviesAdapter = complexRecyclerViewCurrentMoviesAdapter;
        mMovies = movies;
        mContext = context;
    }

    @Override
    public ArrayList<Movie> getCurrentMovies() {
        Log.i("AsyncHTTP","Getting current movies");
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
                                //JsonArray jsonDocsArray = jsonResponseObject.getAsJsonArray(DOCS);
                                Type collectionType = new TypeToken<List<Movie>>() {
                                }.getType();

                                ArrayList<Movie> fetchedMovies = gson.fromJson(jsonResultsArray,
                                        collectionType);
                                Log.i("SearchActivity", fetchedMovies.size() + " movies found");
                                mMovies.clear();
                                mMovies.addAll(fetchedMovies);

                                Log.i("SearchActivity", mMovies.size() + " movies found");
                            }
                        }
                    }
                } catch (JsonSyntaxException e) {
                    Log.w("AsyncHttpClient", "Exception while parsing json " + e.getMessage());
//                    Snackbar.make(, R.string.snackbar_network_error, Snackbar.LENGTH_LONG)
//                            .show(); // Don’t forget to show!
                    Toast.makeText(mContext, "Opps looks like " +
                                    "some problem, try searching again",
                            Toast.LENGTH_SHORT).show();
                }
                mComplexRecyclerViewCurrentMoviesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response,
                                  Throwable throwable) {
                Log.w("AsyncHttpClient", "HTTP Request failure: " + statusCode + " " +
                        throwable.getMessage());

//                if (!isNetworkAvailable()) {
//                    Toast.makeText(getApplicationContext(), "Opps looks like " +
//                                    "network connectivity problem",
//                            Toast.LENGTH_LONG).show();
//                }
//
//                if (!isOnline()) {
//                    Toast.makeText(getApplicationContext(), "Your device is not online, " +
//                                    "check wifi and try again!",
//                            Toast.LENGTH_LONG).show();
//                }

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
