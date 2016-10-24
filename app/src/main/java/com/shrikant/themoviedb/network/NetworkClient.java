package com.shrikant.themoviedb.network;

import com.shrikant.themoviedb.models.Movie;

import java.util.ArrayList;

/**
 * Created by spandhare on 10/23/16.
 */

public interface NetworkClient {
    ArrayList<Movie> getCurrentMovies();
}
