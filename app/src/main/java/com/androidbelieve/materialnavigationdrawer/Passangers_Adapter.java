package com.androidbelieve.materialnavigationdrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;

/**
 * Created by stas on 25/05/16.
 */
public class Passangers_Adapter   extends ArrayAdapter<Tremps> {

    ArrayList<Tremps> tremplist;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
     //facebook  graph object for profile picture
     My_Passangers activity;
    com.facebook.login.widget.ProfilePictureView profilePictureView; //facebook  graph object for profile picture


    public Passangers_Adapter(My_Passangers activity, int resource, ArrayList<Tremps> objects) {  // Constructor for the Adapter
        super(activity.getApplicationContext(), resource, objects);
        vi = (LayoutInflater) activity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        tremplist = objects;

        this.activity = activity;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {             // find elements By id in row_rides_rides.xml

        View v = convertView;
        if (v == null) {

            holder = new ViewHolder();
            v = vi.inflate(Resource, null);

            holder.profilePictureView = (ProfilePictureView) v.findViewById(R.id.picture);
            holder.full_name = (TextView) v.findViewById(R.id.full_name);
            holder.phone = (TextView) v.findViewById(R.id.phone);


            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }


        holder.profilePictureView.setProfileId(tremplist.get(position).getTrempDriverId().toString());
        holder.full_name.setText(tremplist.get(position).getTrempFname() + " " + tremplist.get(position).getTrempLname());
        holder.phone.setText("מספר פלאפון: " + tremplist.get(position).getTrempPhone());

        return v;

    }

    static class ViewHolder {

        public TextView full_name;
        public TextView phone;
        public ProfilePictureView profilePictureView;


    }


}
