package com.shrikant.themoviedb.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.shrikant.themoviedb.MainActivity;
import com.shrikant.themoviedb.R;
import com.shrikant.themoviedb.adapters.RecyclerViewMoviesAdapter;
import com.shrikant.themoviedb.models.Movie;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by spandhare on 10/24/16.
 */

public class OkHttpExample {

    private static final String TAG = "OkHttpExample";
    private final static String API_KEY = "api_key";
    private final static String LANGUAGE = "language";
    private String URL = "https://api.themoviedb.org/3/movie/popular";
    private RecyclerViewMoviesAdapter mRecyclerViewMoviesAdapter;
    private ArrayList<Movie> mMovies;
    private Context mContext;


    public OkHttpExample(RecyclerViewMoviesAdapter recyclerViewMoviesAdapter, ArrayList<Movie> movies, Context context) {
        mRecyclerViewMoviesAdapter = recyclerViewMoviesAdapter;
        mMovies = movies;
        mContext = context;
    }

    public void updatePopularMovies() {
        Request request = new Request.Builder()
                .url(constructQueryRequestParams())
                .build();

        //Asynchronous Network Calls
        // Get a handler that can be used to post to the main thread
        // should be a singleton
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String responseData = response.body().string();
                try {
                    if (responseData != null) {
                        Gson gson = new GsonBuilder().create();
                        JsonObject jsonObject = gson.fromJson(responseData, JsonObject.class);
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
                // Run view-related code back on the main thread
                ((MainActivity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerViewMoviesAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

        });
    }

    public String constructQueryRequestParams() {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL).newBuilder();
        urlBuilder.addQueryParameter(API_KEY, mContext.getString(R.string.api_key));
        urlBuilder.addQueryParameter(LANGUAGE, "en-US");

        return  urlBuilder.build().toString();
    }
}
