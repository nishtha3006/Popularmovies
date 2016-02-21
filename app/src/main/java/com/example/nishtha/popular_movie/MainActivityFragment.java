package com.example.nishtha.popular_movie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    String LOG_TAG="com.example.nishtha.popular_movie";
    MoviesAdapter madapter;
    ArrayList<Movies> movies;
    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }
    private void updateMovie(){
        PopulateMovie populateMovie=new PopulateMovie();
        populateMovie.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        movies=new ArrayList<>();
       /* movies.add(new Movies("http://image.tmdb.org/t/p/w342//24qz9HuZY8K3Cdo4hRWkmztT7gH.jpg",
                "http://image.tmdb.org/t/p/w342//qmDpIHrmpJINaRKAfWQfftjCdyi.jpg"));
        movies.add(new Movies("http://image.tmdb.org/t/p/w342//cezWGskPY5x7GaglTTRN4Fugfb8.jpg",
                "http://image.tmdb.org/t/p/w342//1hRoyzDtpgMU7Dz4JF22RANzQO7.jpg"));
        movies.add(new Movies("http://image.tmdb.org/t/p/w342//h1XjBJoWdOh8aegBoVYKgABQZSL.jpg",
                "http://image.tmdb.org/t/p/w342//uVALAeLEMGMf3oYpvdVi4uuaNOo.jpg"));
        movies.add(new Movies("http://image.tmdb.org/t/p/w342//s2IG9qXfhJYxIttKyroYFBsHwzQ.jpg",
                "http://image.tmdb.org/t/p/w342//s2IG9qXfhJYxIttKyroYFBsHwzQ.jpg"));*/
        madapter=new MoviesAdapter(getActivity(),R.layout.list_item,movies);
        View rootView=inflater.inflate(R.layout.fragment_main, container, false);
        ListView list=(ListView)rootView.findViewById(R.id.list);
        list.setAdapter(madapter);
        return  rootView;
    }

    public class PopulateMovie extends AsyncTask<String[],String,Movie[]> {
        //http://api.themoviedb.org/3/discover/movie?sort_by=vote_count.desc&api_key=d22374f30dea5711f5ae946bed7189f6
        HttpURLConnection urlConnection;
        BufferedReader bufferedReader;
        String movies_detail=null;
        @Override
        protected Movie[] doInBackground(String[]... params) {
            final String BASE_URL="http://api.themoviedb.org/3";
            final String DISCOVER="/discover";
            final String BY_MOVIE="/movie";
            final String SORT_BY_POP="?sort_by=popularity.desc";
            final String SORT_BY_RATINGS="?sort_by=vote_average.desc";
            final String API_KEY="&api_key=d22374f30dea5711f5ae946bed7189f6";
            String path=BASE_URL+DISCOVER+BY_MOVIE+SORT_BY_POP+API_KEY;

            try{
                URL url=new URL(path);
                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream=urlConnection.getInputStream();
                if(inputStream==null){
                    return  null;
                }
                bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer=new StringBuffer();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    buffer.append(line);
                }
                if(buffer.length()==0){
                    Log.d(LOG_TAG, "No fetched the buffer");
                    return null;
                }
                movies_detail=buffer.toString();
            }catch(Exception e){
                Log.e(LOG_TAG,e.getMessage());
            }finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                try{
                    if(bufferedReader!=null){
                        bufferedReader.close();
                    }
                }catch (Exception e){
                    Log.e(LOG_TAG,"reader didnt close properly");
                }
            }
            return getMovieData(movies_detail);
        }
        private Movie[] getMovieData(String movies_detail) {
            final String RESULTS = "results";
            final String TITLE = "original_title";
            final String OVER_VIEW = "overview";
            final String POSTER_PATH = "poster_path";
            final String RELEASE_DATE = "release_date";
            final String RATINGS = "vote_average";
            Movie[] movies=null;
            try {
                JSONObject movie_json = new JSONObject(movies_detail);
                JSONArray movieArray=movie_json.getJSONArray(RESULTS);
                movies=new Movie[movieArray.length()];
                for(int i=0;i<movieArray.length();i++){
                    JSONObject movieObject=movieArray.getJSONObject(i);
                    Movie temp_movie=new Movie();
                    temp_movie.setTitle(movieObject.getString(TITLE));
                    temp_movie.setImage_base_url(movieObject.getString(POSTER_PATH));
                    temp_movie.setOverview(movieObject.getString(OVER_VIEW));
                    temp_movie.setRatings(movieObject.getDouble(RATINGS));
                    temp_movie.setRelease_date(movieObject.getString(RELEASE_DATE));
                    movies[i]=temp_movie;
                }
            }catch (Exception e){
                Log.e(LOG_TAG,e.getMessage());
            }
            return movies;
        }
        @Override
        protected void onPostExecute(Movie[] all_movies) {
            if(all_movies!=null) {
                for(int i=0;i<all_movies.length-1;i=i+2) {
                    Movies temp=new Movies(all_movies[i],all_movies[i+1]);
                    movies.add(temp);
                }
                madapter.addAll(movies);
                madapter.notifyDataSetChanged();

            }
        }
    }
}
