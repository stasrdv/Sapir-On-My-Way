package com.androidbelieve.materialnavigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Transport_Select_Activity extends Activity {


    String source,dest,out_time,free_seats,date,end_time;
    ImageView tremp,transport,integerated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport__select);

        Intent intent = getIntent();
        source=intent.getStringExtra("source");
        dest=intent.getStringExtra("dest");
        out_time=intent.getStringExtra("out_time");
        free_seats=intent.getStringExtra("free_seats");
        date=intent.getStringExtra("date");
        end_time=intent.getStringExtra("end_time");




        tremp = (ImageView) findViewById(R.id.tremp);
        transport = (ImageView) findViewById(R.id.transport);
        integerated = (ImageView) findViewById(R.id.integerated);




        tremp.setClickable(true);
        tremp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(100);

                tremp.setEnabled(false);


                Intent intent = new Intent(Transport_Select_Activity.this, Tremps_Activity.class);
                intent.putExtra("source", source);
                intent.putExtra("dest", dest);
                intent.putExtra("out_time", out_time);
                intent.putExtra("date", date);//Go to Tremps_Activity Activity


                startActivity(intent);

            }
        });



        transport.setClickable(true);
        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(100);

                transport.setEnabled(false);


                Intent intent = new Intent(Transport_Select_Activity.this, Bus_Activity.class); //Go to Bus Activity
                intent.putExtra("source", source);
                intent.putExtra("dest", dest);
                intent.putExtra("out_time", out_time);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });





        integerated.setClickable(true);
        integerated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(100);

                integerated.setEnabled(false);

                Intent intent = new Intent(Transport_Select_Activity.this, Integrated_Activity.class);
                intent.putExtra("source", source);
                intent.putExtra("dest", dest);
                intent.putExtra("out_time", out_time);
                intent.putExtra("date", date);//Go to Tremps_Activity Activity
                intent.putExtra("end_time", end_time);
                startActivity(intent);
                ;
            }
        });










    }
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();


        System.out.println("Restart");
        integerated.setEnabled(true);
        transport.setEnabled(true);
        tremp.setEnabled(true);


    }



    public void onResume() {

        System.out.println("Resume");
        super.onResume();
        integerated.setEnabled(true);
        transport.setEnabled(true);
        tremp.setEnabled(true);



    }
}

