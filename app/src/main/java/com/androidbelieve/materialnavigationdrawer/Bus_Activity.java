package com.androidbelieve.materialnavigationdrawer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
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
import java.util.List;
import java.util.Locale;

public class Bus_Activity extends Activity {

    ArrayList<Bus> busList;
    List<String> VieList = new ArrayList<>();
    Bus_Adapter adapter; //adapter obj
    SwipeRefreshLayout mSwipeRefreshLayout; //swipe layout for refreshing and update
    String source,dest,out_time,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        source=intent.getStringExtra("source");
        dest=intent.getStringExtra("dest");
        out_time=intent.getStringExtra("out_time");
        date = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        busList = new ArrayList<Bus>(); //arrayList of tremps

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout); //refresh data from server on swipe



        new JSONAsyncTask().execute("http://somw3.ddns.net/PublicT.php?"+"source="+source+"&dest="+dest+"&day="+date+
                "&begin_H="+out_time);

        System.out.println("http://somw3.ddns.net/PublicT.php?"+"source="+source+"&dest="+dest+"&day="+date+
                "&begin_H="+out_time);

        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new Bus_Adapter(getApplicationContext(), R.layout.row_bus, busList); //andapter will use the RoW layout

        listview.setAdapter(adapter); //set to the find ride xml the row_rides adapter



        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //when swiping the screeen
                //go to php run query again
                //  new JSONAsyncTask().execute("http://somw3.ddns.net/PublicT.php?source=15544&dest=10053&day=Saturday&begin_H=6:0");
                new JSONAsyncTask().execute("http://somw3.ddns.net/PublicT.php?"+"source="+source+"&dest="+dest+"&day="+date+
                        "&begin_H="+out_time);

                mSwipeRefreshLayout.setRefreshing(false);

            }
        });


    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog; //the small window tha sows conecting to server/loading please wait
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Bus_Activity.this);
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
                    JSONArray jarray = jsono.getJSONArray("publicT"); //we called the Json  in our php file Bus


                    //Run threw the json file and create tremp objects.

                    for (int i = 0; i < jarray.length(); i++) {

                        JSONObject object = jarray.getJSONObject(i);
                        Bus bus = new Bus();
                        bus.setRoute_short_name(object.getString("route_short_name"));
                        bus.setAgency_name(object.getString("agency_name"));
                        bus.setArrival_time(object.getString("arrival_time"));
                        bus.setAgency_id(object.getString("agency_id"));
                        bus.setFinal_time(object.getString("final_time"));






                        if (!VieList.contains(object.getString("route_short_name")) && !VieList.contains(object.getString("arrival_time")) ) {  //don't dipaly same tremp twice...
                            busList.add(bus);
                        }


                        VieList.add(object.getString("route_short_name"));
                        VieList.add(object.getString("arrival_time"));


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
