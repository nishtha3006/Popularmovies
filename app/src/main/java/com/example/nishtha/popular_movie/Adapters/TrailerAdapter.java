package com.example.nishtha.popular_movie.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nishtha.popular_movie.R;
import com.example.nishtha.popular_movie.Trailer;

import java.util.ArrayList;

/**
 * Created by nishtha on 12/4/16.
 */
public class TrailerAdapter extends ArrayAdapter<Trailer> {
    LayoutInflater inflater;
    public TrailerAdapter(Context context,int id,ArrayList<Trailer> trailers){
        super(context,id,trailers);
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Trailer trailer=getItem(position);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_trailer,parent,false);
        }
        TextView title=(TextView)convertView.findViewById(R.id.title);
        title.setText(trailer.getName());
        return convertView;
    }
}
