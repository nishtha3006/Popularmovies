package com.example.nishtha.popular_movie;

/**
 * Created by nishtha on 21/2/16.
 */
public class Movies {
    Movie m1=null,m2=null;
    String url1,url2;
    Movies(Movie m1,Movie m2){
        this.m1=m1;
        this.m2=m2;
    }
    Movies(String url1,String url2){
        this.url1=url1;
        this.url2=url2;
    }
}
