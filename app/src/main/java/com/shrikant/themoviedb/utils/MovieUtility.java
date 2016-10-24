package com.shrikant.themoviedb.utils;

import com.shrikant.themoviedb.models.Movie;

/**
 * Created by spandhare on 10/23/16.
 */

public class MovieUtility {

    private static String PREFIX_URL_CONST = "http://image.tmdb.org/t/p/w500";
    public static String constructURL(Movie movie) {
        return  PREFIX_URL_CONST + movie.getPosterPath();
    }
}
