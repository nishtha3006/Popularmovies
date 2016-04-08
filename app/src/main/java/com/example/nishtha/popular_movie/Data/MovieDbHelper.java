package com.example.nishtha.popular_movie.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by nishtha on 4/4/16.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    final static String DATABASE_NAME="popmovies3.db";
    final static int DATABASE_VERSION=3;

    MovieDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.d("hello","db created nicely");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieEntry.COLUMN_ID + " INTEGER NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_RATINGS + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                "UNIQUE (" + MovieContract.MovieEntry.COLUMN_ID + ") ON CONFLICT REPLACE)";;

        db.execSQL(SQL_CREATE_MOVIE_TABLE);

        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + MovieContract.Favourite.TABLE_NAME + " (" +
                MovieContract.Favourite._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.Favourite.COLUMN_ID + " INTEGER NOT NULL, " +
                MovieContract.Favourite.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.Favourite.COLUMN_OVERVIEW + " TEXT, " +
                MovieContract.Favourite.COLUMN_RATINGS + " TEXT, " +
                MovieContract.Favourite.COLUMN_RELEASE_DATE + " TEXT, " +
                "UNIQUE (" + MovieContract.Favourite.COLUMN_ID + ") ON CONFLICT REPLACE)";;

        db.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("hello","onupgrade");
        db.execSQL("DROP TABLE IF EXISTS "+MovieContract.MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+MovieContract.Favourite.TABLE_NAME);
        onCreate(db);
    }
}
