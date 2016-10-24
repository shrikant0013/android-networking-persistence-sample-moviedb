package com.shrikant.themoviedb.adapters;

import com.shrikant.themoviedb.R;
import com.shrikant.themoviedb.models.Movie;
import com.shrikant.themoviedb.utils.MovieUtility;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spandhare on 10/23/16.
 */

public class ComplexRecyclerViewCurrentMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private static List<Movie> sMovies;
    private static Context mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplexRecyclerViewCurrentMoviesAdapter(Context context, ArrayList<Movie> movies) {
        this.sMovies = movies;
        mContext = context;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.sMovies.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.item_box_office_movie, viewGroup, false);
        RecyclerView.ViewHolder viewHolder = new ImageTextViewHolder(mContext, view, sMovies);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ImageTextViewHolder vh = (ImageTextViewHolder) viewHolder;
        configureImageTextViewMovieViewHolder(vh, position);

    }

    private void configureImageTextViewMovieViewHolder(ImageTextViewHolder viewHolder,
                                                     int position) {
        Movie movie = sMovies.get(position);

        viewHolder.mImageViewPosterImage.setImageResource(0); //clearoff

        Picasso.with(mContext)
                .load(MovieUtility.constructURL(movie))
                .placeholder(R.drawable.small_movie_poster)
                .error(R.drawable.small_movie_poster)
                .into(viewHolder.mImageViewPosterImage);

        viewHolder.mTextViewTitle.setText(movie.getTitle());
    }
}