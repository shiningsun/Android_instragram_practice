package com.sunshineinc.stampus.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CasualHero on 3/16/2015.
 */
public class PhotoListAdapter extends ArrayAdapter<PhotoItem>{

    public PhotoListAdapter(Context context, List<PhotoItem> objects) {
        super(context, R.layout.photo_item, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        PhotoItem photo = getItem(position);
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.photo_item, parent, false);
        }
        ImageView image = (ImageView)view.findViewById(R.id.ivImage);
        TextView text = (TextView)view.findViewById(R.id.tvCaption);
        text.setText(photo.getCaption());
        image.setImageResource(0);
        Picasso.with(getContext()).load(photo.getUrl()).into(image);
        return view;
    }
}
