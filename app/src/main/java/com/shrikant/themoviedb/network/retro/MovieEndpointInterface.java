package com.shrikant.themoviedb.network.retro;

import com.shrikant.themoviedb.models.MovieDetail;
import com.shrikant.themoviedb.models.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by spandhare on 10/23/16.
 */

public interface MovieEndpointInterface {
    @GET("movie/top_rated?language=en-US")
    Call<MovieList> fetchTopRated(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}?language=en-US")
    Call<MovieDetail> getMovieDetails(@Query("movie_id") String movieID,
            @Query("api_key") String apiKey);
}

//@Query("language") String language