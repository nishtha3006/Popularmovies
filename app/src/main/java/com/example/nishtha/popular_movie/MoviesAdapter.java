package com.example.nishtha.popular_movie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nishtha on 21/2/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie>{
    LayoutInflater inflater;
    Context context;
    MoviesAdapter(Context context,int id,ArrayList<Movie> images){
        super(context, id, images);
        this.context=context;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie movie=getItem(position);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.list_item,parent,false);
            Log.d("sunshine","inflate");
        }
        ImageView image1=(ImageView)convertView.findViewById(R.id.image1);
        Log.d("sunshine",movie.getImage_url());
        Picasso.with(context).load(movie.getImage_url()).into(image1);
        return convertView;
    }

}
