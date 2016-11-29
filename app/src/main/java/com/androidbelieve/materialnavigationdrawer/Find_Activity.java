package com.androidbelieve.materialnavigationdrawer;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Find_Activity extends Activity {

    // Declare Variables
    ListView list,list2; // List for source and list for Dest
    Sub_ListViewAdapter adapter,adapter2;//Adapter for each list

    static EditText editsearch,editsearch2; //user's input boxes

    ArrayList<Stop> arraylist = new ArrayList<Stop>();      //ArrayList for Stops
    ArrayList<Stop> arraylist2 = new ArrayList<Stop>();

    String time,time2; //Save data from time Pickers
    String time3;
    Button post;
    Button pickTime;
    String facebook_id;
    private int pHour,pHour2;
    private int pMinute,pMinute2;

    // This integer will uniquely define the dialog to be used for displaying time picker:
    static final int TIME_DIALOG_ID = 0;
    private static final int  TIME_DIALOG_ID1 = 1;


    //Pick Time from time dialog pickers :

    /** Callback received when the user "picks" a time in the dialog */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMinute = minute;
                    time=pHour+":"+pMinute; //Time String
                    time3=pHour+3+":"+pMinute;
                    System.out.println(time3);

                }
            };


    private TimePickerDialog.OnTimeSetListener mTimeSetListener2 =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour2 = hourOfDay;
                    pMinute2 = minute;
                    time2=pHour2+":"+pMinute2; //Time String
                    time3=pHour+3+":"+pMinute;


                }
            };




    public void onCreate(Bundle savedInstanceState) {

        //When user typing,this stop_code avoid from keyboard get over the the layout fields.
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //adjustPan|adjustResize

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        SharedPreferences sharedPref = getSharedPreferences("mySettings", MODE_PRIVATE);   //get user facebook id to recognize him after posting

        facebook_id = sharedPref.getString("mySetting", null); //get data from local store

        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);
        list2 = (ListView) findViewById(R.id.listview2);


        // Read from the Csv file and Display in ListView :
        InputStream inputStream = getResources().openRawResource(R.raw.stops);
        CSVFile csvFile = new CSVFile(inputStream);
        List scoreList = csvFile.read();
        String [] a;
        final String [] stop=new String[scoreList.size()];
        final String [] stopInfo=new String[scoreList.size()];
        final String [] stopCode=new String[scoreList.size()];
        final String [] stopId=new String[scoreList.size()];

        //We have 3 arrays,stop_number,stop_info,stop_code

        for(int i=0;i<scoreList.size();i++){
            a= (String[]) scoreList.get(i);

            stopCode[i]=a[1];
            stop[i]=a[2];
            stopInfo[i]=a[3];
            stopId[i]=a[0];


        }
        //Create Stop Objects

        for (int i = 0; i < scoreList.size(); i++)
        {
            Stop wp = new Stop(stopCode[i], stop[i],
                    stopInfo[i], stopId[i],"1");                   // 1- The Source input Box
            Stop wp2 = new Stop(stopCode[i], stop[i],
                    stopInfo[i], stopId[i],"2");

            // Binds all strings into an array
            arraylist.add(wp);
            arraylist2.add(wp2);
        }

        // Locate the EditText in listview_main.xml
        editsearch = (EditText) findViewById(R.id.search);
        editsearch2 = (EditText) findViewById(R.id.search2);


        // Pass results to ListViewAdapter Class
        adapter = new Sub_ListViewAdapter(this, arraylist);
        adapter2 = new Sub_ListViewAdapter(this, arraylist2);

        // Binds the Adapter to the ListView

        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() { //Set Action listener

            //(the actions after typing in the EditTextBox)

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString();
                adapter.filter(text);       //use The filter as we deined in adapter class
                list.setAdapter(adapter);


            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

        //Same for the second  :::
        editsearch2.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch2.getText().toString();
                adapter2.filter(text);
                list2.setAdapter(adapter2);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });



        /** Capture our View elements */

        pickTime = (Button) findViewById(R.id.pickTime);
        /** Listener for click event of the button */
        pickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

            }
        });


        // Post_Activity Button



        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Create date object
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDateTimeString = sdf.format(c.getTime());

                //Here we get from the user the searchmor parameters
                // we will intent it to Ride_list activity

                find(adapter.getItem(0).getStop_code(), adapter2.getItem(0).getStop_code(), time,time3 ,currentDateTimeString);

            }
        };
        post = (Button) findViewById(R.id.button);

        String src = editsearch.getText().toString();
        String dst = editsearch2.getText().toString();



        post.setOnClickListener(oclBtnOk);

        if(TextUtils.isEmpty(src) || TextUtils.isEmpty(dst)) {
            editsearch.setError("הכנס מוצא");
            editsearch2.setError("הכנס יעד");

            return;
        }

    }

    private void find( String source,String destination, String time,String time3,String currentDateTimeString)
    {

        ///send data to another activity

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm ");

        date.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));

        String localTime = date.format(currentLocalTime);





        Intent myIntent = new Intent(Find_Activity.this, Transport_Select_Activity.class);
        myIntent.putExtra("source",source );
        myIntent.putExtra("dest",destination);
        myIntent.putExtra("out_time", time);
        myIntent.putExtra("end_time", time3);
        myIntent.putExtra("date",currentDateTimeString );
        Find_Activity.this.startActivity(myIntent);
    }


    /** Create a new dialog for time picker */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, pHour, pMinute, false);


            case TIME_DIALOG_ID1:
                return new TimePickerDialog(this,
                        mTimeSetListener2, pHour2, pMinute2, false);
        }
        return null;
    }

}







