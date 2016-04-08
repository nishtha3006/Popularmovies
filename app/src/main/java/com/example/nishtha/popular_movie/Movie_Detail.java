package com.example.nishtha.popular_movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Movie_Detail extends AppCompatActivity {

    @Bind(R.id.title) TextView title;
    @Bind(R.id.overview)TextView overview;
    @Bind(R.id.release)TextView release_date;
    @Bind(R.id.ratings)TextView ratings;
    @Bind(R.id.poster)ImageView poster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Movie temp_movie=getIntent().getParcelableExtra("movie");
        ButterKnife.bind(this);
        title.setText("Title : " + temp_movie.getTitle());
        overview.setText("Overview : "+temp_movie.getOverview());
        release_date.setText("Release Date : " + temp_movie.getRelease_date());
        ratings.setText("Vote_avg : " +temp_movie.getRatings());
        String final_url=temp_movie.getImage_url();
        Picasso.with(this).load(final_url).resize(700,780).into(poster);
    }
}
