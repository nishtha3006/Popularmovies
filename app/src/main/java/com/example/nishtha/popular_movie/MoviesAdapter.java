package com.example.nishtha.popular_movie;

import android.content.Context;
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
public class MoviesAdapter extends ArrayAdapter<Movies>{
    LayoutInflater inflater;
    Context context;
    MoviesAdapter(Context context,int id,ArrayList<Movies> images){
        super(context, id, images);
        this.context=context;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movies movie_pair=getItem(position);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.list_item,parent,false);
        }
        ImageView image1=(ImageView)convertView.findViewById(R.id.image1);
        ImageView image2=(ImageView)convertView.findViewById(R.id.image2);
        Picasso.with(context).load(movie_pair.m1.getImage_url()).into(image1);
        Picasso.with(context).load(movie_pair.m2.getImage_url()).into(image2);
        return convertView;
    }
}
