package com.androidbelieve.materialnavigationdrawer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Integrated_Activity extends Activity {

    ArrayList<Integrated> integratedArrayList;
    List<String> VieList = new ArrayList<>();
    Integrated_Adapter adapter; //adapter obj
    SwipeRefreshLayout mSwipeRefreshLayout; //swipe layout for refreshing and update
    String date,source,dest,out_time,day,end_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        Intent intent = getIntent();
        source=intent.getStringExtra("source");
        dest=intent.getStringExtra("dest");
        out_time=intent.getStringExtra("out_time");
        end_time=intent.getStringExtra("end_time");



        date=intent.getStringExtra("date");
        day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());


        integratedArrayList=new ArrayList<Integrated>();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout); //refresh data from server on swipe

        new JSONAsyncTask().execute("http://somw3.ddns.net/tremps_combine.php?"+"source="+source
                +"&dest="+dest+"&date="+date+"&day="+day+
                "&begin_H="+out_time+"&end_H="+end_time); //go to this adress and fetch the query

        System.out.println("http://somw3.ddns.net/tremps_combine.php?"+"source="+source
                +"&dest="+dest+"&date="+date+"&day="+day+
                "&begin_H="+out_time+"&end_H="+end_time);


        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new Integrated_Adapter(this, R.layout.row_integrated, integratedArrayList); //andapter will use the RoW layout

        listview.setAdapter(adapter); //set to the find ride xml the row_rides adapter



    }


    public void buttonClicked(Integrated tremps)
    {
        SharedPreferences sharedPref = getSharedPreferences("mySettings", MODE_PRIVATE);
        String full_name = sharedPref.getString("mySetting2", null);
        String Myid = sharedPref.getString("mySetting", null);
        String trempId = tremps.getTrempID();

        //Manage The Tremp:
        new JSonTask().execute("http://somw3.ddns.net/join2tremp.php?" + "user_id=" + Myid + "&tremp_id="
                + trempId);

        System.out.println("http://somw3.ddns.net/join2tremp.php?" + "user_id=" + Myid + "&tremp_id="
                + trempId);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + tremps.getTrempPhone()));
        intent.putExtra("sms_body",
                "   שלום שמי    "
                        + full_name +
                        "   ראיתי שפרסמת טרמפ בשעה  "
                        + tremps.getTrempOutTime() +
                        " האם   אפשר להצטרף?      "

        );
        startActivity(intent);

    }

    class JSonTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog; //the small window tha sows conecting to server/loading please wait
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Integrated_Activity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {

            try {

                //the  recourse to server
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    return false;
                }

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
            return false;
        }

        protected void onPostExecute(Boolean result) {      //if connection failed
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "Tremp Request Sent To", Toast.LENGTH_LONG).show();

        }
    }



    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog; //the small window tha sows conecting to server/loading please wait
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Integrated_Activity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {

            try {
                //the  recourse to server
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    JSONObject jsono = new JSONObject(data);//after sql query we encoded the result to Json
                    JSONArray jarray = jsono.getJSONArray("Tremps"); //we called the Json  in our php file Tremps


                    //Run threw the json file and create tremp objects.

                    for (int i = 0; i < jarray.length(); i++) {

                        JSONObject object = jarray.getJSONObject(i);

                        Integrated obj = new Integrated();

                        //Tremp Fields
                        obj.setTrempDriverId(object.getString("user_id"));
                        obj.setTrempID(object.getString("id"));
                        obj.setTrempFname(object.getString("user_name"));
                        obj.setTrempLname(object.getString("user_lname"));
                        obj.setTrempPhone(object.getString("user_phone"));
                        obj.setGender(object.getString("gender"));
                        obj.setSmoke(Integer.parseInt(object.getString("smoke")));
                        obj.setRaiting(Integer.parseInt(object.getString("raiting")));
                        obj.setNum_of_ratings(Integer.parseInt(object.getString("no_of_raitings")));
                        obj.setTrempPassangers(Integer.parseInt(object.getString("no_p")));
                        obj.setTrempSource(object.getString("stop_name"));
                        obj.setTrempDest(object.getString("stop_name_dest"));

                        obj.setTrempOutTime(object.getString("out_time"));
                        obj.setTrempArriveTime(object.getString("arrive_time"));

                        //Bus Fields
                        obj.setAgency_id(object.getString("agency_id"));
                        obj.setRoute_short_name(object.getString("route_short_name"));
                        obj.setAgency_name(object.getString("agency_name"));

                        obj.setBusArrival_time(object.getString("arrival_time"));
                        obj.setFinalTime(object.getString("final_time"));




                        //if (!VieList.contains(object.getString("id"))) {  //don't dipaly same tremp twice...
                            integratedArrayList.add(obj);
                            // }
                       // }


                        //VieList.add(object.getString("id"));

                    }
                    return false;
                }




            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {      //if connection failed
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "Successfully Loaded", Toast.LENGTH_LONG).show();

        }
    }



}
