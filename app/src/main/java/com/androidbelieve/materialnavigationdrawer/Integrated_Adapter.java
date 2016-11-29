package com.androidbelieve.materialnavigationdrawer;

import android.content.Context;
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
 * Created by stas on 18/05/16.
 */
public class Integrated_Adapter extends ArrayAdapter<Integrated> {
    ArrayList<Integrated> integratedList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    Integrated_Activity activity;
    ProfilePictureView profilePictureView; //facebook  graph object for profile picture


    public Integrated_Adapter(Integrated_Activity activity, int resource, ArrayList<Integrated> objects) {  // Constructor for the Adapter
        super(activity, resource, objects);
        vi = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        integratedList = objects;
        this.activity=activity;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {             // find elements By id in row_rides_rides.xml
        //elements in ROW layout

        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);


            holder.profilePictureView = (ProfilePictureView) v.findViewById(R.id.picture);
            holder.name = (TextView) v.findViewById(R.id.full_name);
            holder.num_rating = (TextView) v.findViewById(R.id.num_ratings);
            holder.ratingBar = (RatingBar) v.findViewById(R.id.ratingBar2);
            holder.source = (TextView) v.findViewById(R.id.source);
            holder.dest = (TextView) v.findViewById(R.id.dest);
            holder.out_time = (TextView) v.findViewById(R.id.out_time);
            holder.arrive_time = (TextView) v.findViewById(R.id.arrive_time);
            holder.passengers = (TextView) v.findViewById(R.id.passangers);
            holder.phone = (TextView) v.findViewById(R.id.phone);
            holder.payment=(ImageView) v.findViewById(R.id.payment);
            holder.join = (Button) v.findViewById(R.id.joinButton);


            final Integrated temp = integratedList.get(position);

            holder.join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.buttonClicked(temp);


                }
            });

            holder.route_short_name = (TextView) v.findViewById(R.id.route_short_name);
            holder.agency_name = (TextView) v.findViewById(R.id.agency_name);
            holder.arrival_time = (TextView) v.findViewById(R.id.arrival_time);
            holder.final_time = (TextView) v.findViewById(R.id.final_time);
            holder.bus_image= (ImageView) v.findViewById(R.id.imageView1);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }


        //Set data from the Tremp Object to the listview:
        holder.profilePictureView.setProfileId(integratedList.get(position).getTrempDriverId().toString());
        holder.name.setText(integratedList.get(position).getTrempFname() + " " + integratedList.get(position).getTrempLname());
        holder.num_rating.setText("מספר מדרגים:" + integratedList.get(position).getNum_of_ratings());
        holder.ratingBar.setNumStars(((Integer) integratedList.get(position).getRaiting()));
        holder.source.setText("מוצא:" + integratedList.get(position).getTrempSource());
        holder.dest.setText("יעד: " + integratedList.get(position).getTrempDest());
        holder.out_time.setText("שעת יציאה: " + integratedList.get(position).getTrempOutTime());
        holder.arrive_time.setText(" שעת הגעה: " + integratedList.get(position).getTrempArriveTime());

        holder.passengers.setText("מקומות פנויים : " + integratedList.get(position).getTrempPassangers());
        holder.phone.setText("מספר פלאפון: " + integratedList.get(position).getTrempPhone());



        if(integratedList.get(position).getAgency_id().equals("31")) {
            holder.bus_image.setImageResource(R.drawable.dandarom);
        }

        else if(integratedList.get(position).getAgency_id().equals("15")) {
            holder.bus_image.setImageResource(R.drawable.metropoline);
        }
        else if(integratedList.get(position).getAgency_id().equals("3")) {
            holder.bus_image.setImageResource(R.drawable.eged);
        }
        else if(integratedList.get(position).getAgency_id().equals("4")) {
            holder.bus_image.setImageResource(R.drawable.egged_taabura);
        }
        else if(integratedList.get(position).getAgency_id().equals("5")) {
            holder.bus_image.setImageResource(R.drawable.dan);
        }
        else if(integratedList.get(position).getAgency_id().equals("23")) {
            holder.bus_image.setImageResource(R.drawable.galim);
        }

        else{
            holder.bus_image.setImageResource(R.drawable.bus);
        }

        holder.route_short_name.setText("מספר קו :" + integratedList.get(position).getRoute_short_name());
        holder.agency_name.setText("מפעיל : " + integratedList.get(position).getAgency_name());

        holder.arrival_time.setText("זמן יציאה : " + integratedList.get(position).getBusArrival_time());
        holder.final_time.setText("זמן הגעה : " + integratedList.get(position).getFinalTime());

        return v;

    }

    static class ViewHolder {
        //Driver Details
        public ProfilePictureView profilePictureView;
        public TextView name;
        public TextView num_rating;
        public RatingBar ratingBar;
        public TextView source;
        public TextView dest;
        public TextView out_time;
        public TextView arrive_time;
        public TextView passengers;
        public TextView phone;
        public Button join;
        public ImageView payment;



        //Bus Details
        public TextView route_short_name;
        public TextView agency_name;
        public TextView final_time;
        public TextView arrival_time;
        public ImageView bus_image;



    }


}
