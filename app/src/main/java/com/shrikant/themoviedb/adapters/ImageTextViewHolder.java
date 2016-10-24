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

    public ImageTextViewHolder(Context context, View view, List<Movie> movies) {
        super(view);

        this.mMovies = movies;
        this.mContext = context;

        // Attach a click listener to the entire row view
        view.setOnClickListener(this);
        ButterKnife.bind(this, view);
    }

    // Handles the row being being clicked
    @Override
    public void onClick(View view) {

//        int position = getLayoutPosition(); // gets item position
//        Movie movie = mTweets.get(position);
//        // We can access the data within the views
        Toast.makeText(mContext, "Loading movie...", Toast.LENGTH_SHORT).show();
//
//        Intent i = new Intent(mContext, TweetDetailActivity.class);
//        i.putExtra("movie",movie);
//        mContext.startActivity(i);
    }
}

