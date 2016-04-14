package com.example.nishtha.popular_movie.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nishtha.popular_movie.R;
import com.example.nishtha.popular_movie.Review;

import java.util.ArrayList;

/**
 * Created by nishtha on 13/4/16.
 */
public class ReviewAdapter extends ArrayAdapter<Review> {
    LayoutInflater inflater;
    public ReviewAdapter(Context context,int id,ArrayList<Review> reviews){
        super(context,id,reviews);
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review temp_review=getItem(position);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_review,parent,false);
        }
        TextView text=(TextView)convertView.findViewById(R.id.author);
        text.setText(temp_review.getAuthor());
        return convertView;
    }
}
