package com.shrikant.themoviedb.models;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by spandhare on 10/24/16.
 */

@Database(name = MovieDatabase.NAME, version = MovieDatabase.VERSION)
public class MovieDatabase {
    public static final String NAME = "MyMoviesDataBase";

    public static final int VERSION = 1;
}
