package com.shrikant.themoviedb.fragments;

import com.shrikant.themoviedb.R;
import com.shrikant.themoviedb.network.AsyncTaskExample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by spandhare on 10/23/16.
 */

public class FavouriteMovieFragment extends Fragment {

    private static final String TAG = "FavouriteMovieFragment";
    String urlString = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg";

    @BindView(R.id.ivFavouriteMovieImage)
    ImageView mFavouriteMovieImage;

    @BindView(R.id.tvFavouriteMovieTitle)
    TextView mFavouriteMovieTitleTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);

        View v = inflater.inflate(R.layout.favourite_movie_layout, parent, false);
        ButterKnife.bind(this, v);

        Log.i(TAG, "Calling AsyncTask");

        //grabImageFromOnline();
        populateFavouriteMovie();
        return v;
    }

    private void grabImageFromOnline() {
        //This doesn't work because network calls cannot be
        // made on UI thread
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            //Create new url
            url = new URL(urlString);

            //Open a connection
            urlConnection = (HttpURLConnection) url
                    .openConnection();

            //Grab the input stream
            InputStream in = urlConnection.getInputStream();

            //Convert stream to Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            in.close();

            // Load Bitmap into ImageView
            mFavouriteMovieImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void populateFavouriteMovie() {
        AsyncTaskExample obj = new AsyncTaskExample(mFavouriteMovieImage,
                mFavouriteMovieTitleTextView, getContext());
        obj.showfavouriteMovie();
    }
}
