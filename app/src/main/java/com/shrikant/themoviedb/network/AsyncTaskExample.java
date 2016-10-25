package com.shrikant.themoviedb.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.shrikant.themoviedb.R;
import com.shrikant.themoviedb.models.MovieDetail;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by spandhare on 10/23/16.
 */

public class AsyncTaskExample {

    private String TAG = "AsyncTaskExample";
    private ImageView mFavouriteMovieImage;
    private TextView mTextView;
    private Context mContext;
    private String URL = "https://api.themoviedb.org/3/movie/76341?language=en-US&api_key=";
    private String POSTER_URL = "https://image.tmdb.org/t/p/w300/kqjL17yufvn9OVLyXYpvtyrFfak.jpg";

    public AsyncTaskExample( ImageView imageView, TextView textView, Context context) {
        mFavouriteMovieImage = imageView;
        mTextView = textView;
        mContext = context;
        URL = URL +  mContext.getString(R.string.api_key);
    }

    public void showfavouriteMovie() {
        new JSONAsyncTask().execute(URL);
        new ImageLoaderAsyncTask().execute(POSTER_URL);

    }
    private class ImageLoaderAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("Getting Image ...");
            dialog.show();
        }

        public Bitmap doInBackground(String... strings) {
            Log.i(TAG, "doing in background");
            URL url;
            HttpURLConnection urlConnection = null;
            Bitmap bitmap = null;
            try {
                //Create new url
                url = new URL(strings[0]);

                Log.i(TAG, "opening connections");
                //Open a connection
                urlConnection = (HttpURLConnection) url
                        .openConnection();

                Log.i(TAG, "getting input stream");
                //Grab the input stream
                InputStream in = urlConnection.getInputStream();

                //Convert stream to Bitmap
                bitmap = BitmapFactory.decodeStream(in);
                in.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            dialog.dismiss();
            // This method is executed in the UIThread
            // with access to the result of the task
            // Load Bitmap into ImageView
            Log.i(TAG, "setting bitmap");
            mFavouriteMovieImage.setImageBitmap(result);
        }
    }
    //Getting JSOn from online with AsyncTask
    private class JSONAsyncTask extends AsyncTask<String, String, JsonObject> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("Getting Data ...");
            dialog.show();

        }

        @Override
        protected JsonObject doInBackground(String... args) {
            Log.i(TAG, "Starting JsonAsyncTask in background");
            URL url;
            HttpURLConnection urlConnection = null;
            JSONObject jsonDict = null;
            JsonObject json = null;
            try {
                //Create new url
                url = new URL(args[0]);

                //Open a connection
                urlConnection = (HttpURLConnection) url
                        .openConnection();

                //Grab the input stream
                InputStream in = urlConnection.getInputStream();

                //Convert stream to json
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                in.close();

                Gson gson = new GsonBuilder().create();
                json =  gson.fromJson(stringBuilder.toString(), JsonObject.class);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            Log.i(TAG, "Exiting JsonAsyncTask in background");
            return json;
        }

        @Override
        protected void onPostExecute(JsonObject json) {
            dialog.dismiss();
            try {
                Gson gson = new GsonBuilder().create();
                Type collectionType = new TypeToken<MovieDetail>() {
                }.getType();

                MovieDetail fetchedMovie = gson.fromJson(json,
                        collectionType);
                Log.i(TAG, fetchedMovie.getTitle());

                mTextView.setText(fetchedMovie.getTitle());

            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
