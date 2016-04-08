package com.example.nishtha.popular_movie;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.nishtha.popular_movie.Data.MovieContract.MovieEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * Created by nishtha on 4/4/16.
 */
public class FetchMovies extends AsyncTask<String,Void,Void> {
    String LOG_TAG="hello";
    HttpURLConnection urlConnection=null;
    BufferedReader reader;
    String moviesJsonStr;
    Context mcontex;
    FetchMovies(Context context){
        mcontex=context;
    }
    @Override
    protected Void doInBackground(String... params) {
        try{
        final String baseUrl = "http://api.themoviedb.org/3/discover/movie?";
        final String SORT_PARAM = "sort_by";
        final String API_KEY = "api_key";

        Uri queryUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(SORT_PARAM, params[0])
                .appendQueryParameter(API_KEY,"d22374f30dea5711f5ae946bed7189f6")
                .build();

        URL url = new URL(queryUri.toString());
        Log.v(LOG_TAG, "THE URL IS: " + url);

        // Create the request to OpenWeatherMap, and open the connection
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        Log.d("hello","connected");
        // Read the input stream into a String
        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {

        }
        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }

        if (buffer.length() == 0) {

        }
        moviesJsonStr = buffer.toString();
            Log.v("Result", moviesJsonStr);
        getMovieData(moviesJsonStr);

    }catch(Exception e){
            Log.e(LOG_TAG,e.getMessage());
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            try{
                if(reader!=null){
                    reader.close();
                }
            }catch (Exception e){
                Log.e(LOG_TAG,"reader didnt close properly");
            }
        }
        return null;
    }
    private void getMovieData(String movies_detail) {
        final String RESULTS = "results";
        final String TITLE = "original_title";
        final String OVER_VIEW = "overview";
        final String POSTER_PATH = "poster_path";
        final String RELEASE_DATE = "release_date";
        final String RATINGS = "vote_average";
        final String ID="id";
        try{
        JSONObject movie_json = new JSONObject(movies_detail);
        JSONArray movieArray=movie_json.getJSONArray(RESULTS);
            Vector<ContentValues> cVector=new Vector<>(movieArray.length());
            //int deleted = mcontex.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, null, null);
            Log.d(LOG_TAG, "Previous data wiping Complete. "  + " Deleted");
            for(int i=0;i<movieArray.length();i++){
                JSONObject movieObject=movieArray.getJSONObject(i);
                ContentValues value=new ContentValues();
                int id=movieObject.getInt(ID);
                value.put(MovieEntry.COLUMN_ID, id);
                String title=movieObject.getString(TITLE);
                value.put(MovieEntry.COLUMN_TITLE, title);
                String over_view=movieObject.getString(OVER_VIEW);
                value.put(MovieEntry.COLUMN_OVERVIEW, over_view);
                String final_url="http://image.tmdb.org/t/p/w342/"+movieObject.getString(POSTER_PATH);
                Log.d("hello",final_url);
                value.put(MovieEntry.COLUMN_IMAGE_PATH, final_url);
                String release_date=movieObject.getString(RELEASE_DATE);
                value.put(MovieEntry.COLUMN_RELEASE_DATE,release_date);
                double ratings=movieObject.getDouble(RATINGS);
                value.put(MovieEntry.COLUMN_RATINGS,ratings);
                cVector.add(value);
               // mcontex.getContentResolver().insert(MovieEntry.CONTENT_URI,value);
            }
            int inserted=0;
            ContentValues[] values=new ContentValues[cVector.size()];
            cVector.toArray(values);
            inserted=mcontex.getContentResolver().bulkInsert(MovieEntry.CONTENT_URI,values);
            Log.d("hello ",inserted+" rows has been inserted");
        }catch (Exception e){
            Log.e(LOG_TAG,e.getMessage());
        }

    }

}
