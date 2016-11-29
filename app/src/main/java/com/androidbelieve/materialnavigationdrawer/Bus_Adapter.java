package com.androidbelieve.materialnavigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

/**
 * Created by USER on 08/05/2016.
 */
public class Bus_Adapter extends ArrayAdapter<Bus> {

    ArrayList<Bus> buslist;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public Bus_Adapter(Context context, int resource, ArrayList<Bus> objects) {  // Constructor for the Adapter
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        buslist = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {             // find elements By id in row_rides_rides.xml
        //elements in ROW layout

        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);


            holder.route_short_name = (TextView) v.findViewById(R.id.route_short_name);
            holder.agency_name = (TextView) v.findViewById(R.id.agency_name);
            holder.arrival_time = (TextView) v.findViewById(R.id.arrival_time);
            holder.final_time=(TextView) v.findViewById(R.id.final_time);
            holder.bus_image= (ImageView) v.findViewById(R.id.imageView1);


            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }




        if(buslist.get(position).getAgency_id().equals("31")) {
            holder.bus_image.setImageResource(R.drawable.dandarom);
        }

        else if(buslist.get(position).getAgency_id().equals("15")) {
            holder.bus_image.setImageResource(R.drawable.metropoline);
        }
        else if(buslist.get(position).getAgency_id().equals("3")) {
            holder.bus_image.setImageResource(R.drawable.eged);
        }
        else if(buslist.get(position).getAgency_id().equals("4")) {
            holder.bus_image.setImageResource(R.drawable.egged_taabura);
        }
        else if(buslist.get(position).getAgency_id().equals("5")) {
            holder.bus_image.setImageResource(R.drawable.dan);
        }
        else if(buslist.get(position).getAgency_id().equals("23")) {
            holder.bus_image.setImageResource(R.drawable.galim);
        }

        else{
            holder.bus_image.setImageResource(R.drawable.bus);
        }

        holder.route_short_name.setText("מספר קו:" + buslist.get(position).getRoute_short_name());
        holder.agency_name.setText("מפעיל:" + buslist.get(position).getAgency_name());
        holder.arrival_time.setText("זמן יציאה:" + buslist.get(position).getArrival_time());
        holder.final_time.setText("זמן הגעה:" + buslist.get(position).getFinal_time());


        return v;

    }

    static class ViewHolder {
        // public   ProfilePictureView profilePictureView;
        public TextView route_short_name;
        public TextView final_time;
        public TextView agency_name;
        public TextView arrival_time;
        public ImageView bus_image;




    }

}
