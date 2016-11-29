package com.androidbelieve.materialnavigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.login.widget.*;

import java.util.ArrayList;

/**
 * Created by stas on 25/05/16.
 */
public class MyRides_Adapter extends ArrayAdapter<Tremps> {

    ArrayList<Tremps> tremplist;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    ProfilePictureView profilePictureView; //facebook  graph object for profile picture
    Myrides_Activity activity;


    public MyRides_Adapter(Myrides_Activity activity, int resource, ArrayList<Tremps> objects) {  // Constructor for the Adapter
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

            holder.source = (TextView) v.findViewById(R.id.source);
            holder.dest = (TextView) v.findViewById(R.id.dest);
            holder.out_time = (TextView) v.findViewById(R.id.out_time);
            holder.date = (TextView) v.findViewById(R.id.date);




            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }


        holder.date.setText("תאריך:" +tremplist.get(position).getDate());
        holder.source.setText("מוצא:" + tremplist.get(position).getTrempSource());
        holder.dest.setText("יעד: " + tremplist.get(position).getTrempDest());
        holder.out_time.setText("שעת יציאה: " + tremplist.get(position).getTrempOutTime());




        return v;

    }

    static class ViewHolder {


        public TextView source;
        public TextView dest;
        public TextView out_time;
        public TextView date;





    }



}
