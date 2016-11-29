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

//Adapter class to dispaly the tremp in listview

public class Tremp_Adapter extends ArrayAdapter<Tremps> {

    ArrayList<Tremps> tremplist;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    ProfilePictureView profilePictureView; //facebook  graph object for profile picture
    Tremps_Activity activity;
    Bitmap bitmap;
    ImageView img;

    public Tremp_Adapter(Tremps_Activity activity, int resource, ArrayList<Tremps> objects) {  // Constructor for the Adapter
        super(activity.getApplicationContext(), resource, objects);
        vi = (LayoutInflater) activity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        tremplist = objects;

        this.activity = activity;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {             // find elements By id in row_rides_rides.xml

        //holder suck... u will have other probs later...
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
            holder.passengers = (TextView) v.findViewById(R.id.passangers);
            holder.phone = (TextView) v.findViewById(R.id.phone);
            holder.payment=(ImageView) v.findViewById(R.id.smoke);
            holder.smoking=(ImageView) v.findViewById(R.id.dollar);

            holder.join = (Button) v.findViewById(R.id.joinButton);

            final Tremps temp = tremplist.get(position);

            holder.join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.buttonClicked(temp);


                }
            });
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }


        //Set data from the Tremp Object to the listview:

        holder.profilePictureView.setProfileId(tremplist.get(position).getTrempDriverId().toString());
        holder.name.setText(tremplist.get(position).getTrempFname() + " " + tremplist.get(position).getTrempLname());
        holder.num_rating.setText("מספר מדרגים:" + tremplist.get(position).getNum_of_ratings());
        holder.ratingBar.setNumStars(((Integer) tremplist.get(position).getRaiting()/tremplist.get(position).getNum_of_ratings()));


        holder.source.setText("מוצא:" + tremplist.get(position).getTrempSource());
        holder.dest.setText("יעד: " + tremplist.get(position).getTrempDest());
        holder.out_time.setText("שעת יציאה: " + tremplist.get(position).getTrempOutTime());
        holder.passengers.setText("מקומות פנויים : " + tremplist.get(position).getTrempPassangers());
        holder.phone.setText("מספר פלאפון: " + tremplist.get(position).getTrempPhone());


        if(tremplist.get(position).getPay()==(1)) {
            holder.payment.setImageResource(R.drawable.dfdf);
        }

        if(tremplist.get(position).getSmoke()==(1)) {
            holder.smoking.setImageResource(R.drawable.smoke);
        }

        return v;

    }

    static class ViewHolder {

        public ProfilePictureView profilePictureView;
        public TextView name;
        public TextView num_rating;
        public RatingBar ratingBar;
        public TextView source;
        public TextView dest;
        public TextView out_time;
        public TextView passengers;
        public TextView phone;
        public Button join;
        public ImageView payment;
        public ImageView smoking;





    }
}