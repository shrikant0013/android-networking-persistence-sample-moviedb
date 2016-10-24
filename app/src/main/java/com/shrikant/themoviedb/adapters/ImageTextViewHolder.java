package com.shrikant.themoviedb.adapters;

import com.shrikant.themoviedb.R;
import com.shrikant.themoviedb.models.Movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by spandhare on 10/23/16.
 */

public class ImageTextViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    @BindView(R.id.ivPosterImage)
    ImageView mImageViewPosterImage;

    @BindView(R.id.tvTitle)
    TextView mTextViewTitle;

    @BindView(R.id.tvCriticsScore) TextView mTextViewCriticsScore;

    @BindView(R.id.tvCast) TextView mTextViewCast;

    List<Movie> mMovies;
    Context mContext;

    private RecyclerViewMoviesAdapter.MoviePersistsListener mMoviePersistsListener;

    public ImageTextViewHolder(Context context, View view, List<Movie> movies,
                               RecyclerViewMoviesAdapter.MoviePersistsListener listener) {
        super(view);

        this.mMovies = movies;
        this.mContext = context;
        this.mMoviePersistsListener = listener;

        // Attach a click listener to the entire row view
        view.setOnClickListener(this);
        ButterKnife.bind(this, view);
    }

    // Handles the row being being clicked
    @Override
    public void onClick(View view) {

        int position = getLayoutPosition(); // gets item position
        Movie movie = mMovies.get(position);

        if (mMoviePersistsListener != null) {
            mMoviePersistsListener.onMovieClick(movie);
        }

        // We can access the data within the views
        //movie.save();
        Toast.makeText(mContext, "Clicked movie...", Toast.LENGTH_SHORT).show();
    }
}