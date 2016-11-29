package com.androidbelieve.materialnavigationdrawer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Tremps_Activity extends Activity {


    // Find_Activity ride activity use Activity find ride XML that XML use the ROW adapter listview .
    ArrayList<Tremps> trempsList;
    List<String> VieList = new ArrayList<>();
    Tremp_Adapter adapter; //adapter obj
    SwipeRefreshLayout mSwipeRefreshLayout; //swipe layout for refreshing and update
    String date,source,dest,out_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        source=intent.getStringExtra("source");
        dest=intent.getStringExtra("dest");
        out_time=intent.getStringExtra("out_time");
        date=intent.getStringExtra("date");


        setContentView(R.layout.activity_list);

        trempsList = new ArrayList<Tremps>(); //arrayList of tremps

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout); //refresh data from server on swipe

        new JSONAsyncTask().execute("http://somw3.ddns.net/tremps.php?" + "source=" + source + "&dest=" + dest + "&date=" + date +
                "&begin_H=" + out_time); //go to this adress and fetch the query
System.out.println("http://somw3.ddns.net/tremps.php?" + "source=" + source + "&dest=" + dest + "&date=" + date +
        "&begin_H=" + out_time);
         ListView listview = (ListView)findViewById(R.id.list);


        adapter = new Tremp_Adapter(this, R.layout.row_rides, trempsList); //adapter will use the RoW layout

        listview.setAdapter(adapter); //set to the find ride xml the row_rides adapter


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //when swiping the screeen
                //go to php run query again
                new JSONAsyncTask().execute("http://somw3.ddns.net/tremps.php?" + "source=" + source + "&dest=" + dest + "&date=" + date +
                        "&begin_H=" + out_time);


                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog; //the small window tha sows conecting to server/loading please wait
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Tremps_Activity.this);
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

                        Tremps tremp = new Tremps();


    tremp.setTrempID(object.getString("id"));
    tremp.setTrempDriverId(object.getString("user_id"));
    tremp.setTrempFname(object.getString("user_name"));
    tremp.setTrempLname(object.getString("user_lname"));
    tremp.setTrempPhone(object.getString("user_phone"));
    tremp.setGender(object.getString("gender"));
    tremp.setSmoke(Integer.parseInt(object.getString("smoke")));
    tremp.setRaiting(Integer.parseInt(object.getString("raiting")));

    tremp.setPay(Integer.parseInt(object.getString("payment")));
    tremp.setNum_of_ratings(Integer.parseInt(object.getString("no_of_raitings")));
    tremp.setTrempPassangers(Integer.parseInt(object.getString("no_p")));
    tremp.setTrempSource(object.getString("stop_name"));
    tremp.setTrempDest(object.getString("stop_name_dest"));
    tremp.setTrempOutTime(object.getString("out_time"));
    tremp.setTrempArriveTime(object.getString("arrive_time"));


                        //when refresh by swipe,we don'd want to see same tremps twice, or inactive tremps

                        if (!VieList.contains(object.getString("id"))) {  //don't dipaly same tremp twice...

                            if( ! object.getString("user_id").equals(MainActivity.id)) {
                                trempsList.add(tremp);
                            }

                        }


                       VieList.add(object.getString("id"));

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

    class JSonTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog; //the small window tha sows conecting to server/loading please wait
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Tremps_Activity.this);
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

    public void buttonClicked(Tremps tremps)
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



}





