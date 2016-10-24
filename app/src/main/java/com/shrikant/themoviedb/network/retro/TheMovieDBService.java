package com.shrikant.themoviedb.network.retro;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by spandhare on 10/23/16.
 */

public class TheMovieDBService {
    public static String REST_END_POINT = "https://api.themoviedb.org/3/";

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(REST_END_POINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static MovieEndpointInterface topMovies =
            retrofit.create(MovieEndpointInterface.class);
}
