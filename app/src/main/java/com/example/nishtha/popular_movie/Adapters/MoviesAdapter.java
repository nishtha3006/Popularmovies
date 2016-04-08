package com.example.nishtha.popular_movie.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.nishtha.popular_movie.Movie;
import com.example.nishtha.popular_movie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nishtha on 21/2/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie>{
    LayoutInflater inflater;
    Context context;
    public MoviesAdapter(Context context,int id,ArrayList<Movie> images){
        super(context, id, images);
        this.context=context;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie movie=getItem(position);
        ViewHolder holder;
        if(convertView!=null){
            holder = (ViewHolder) convertView.getTag();
        }else {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        if(movie.image_url!=null) {
            Picasso.with(context).load(movie.getImage_url()).into(holder.imageView);
        }else
            holder.imageView.setImageResource(R.drawable.down);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.image1)
        ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
