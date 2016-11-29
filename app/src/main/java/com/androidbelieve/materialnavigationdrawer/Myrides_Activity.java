package com.androidbelieve.materialnavigationdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.List;

public class Myrides_Activity extends AppCompatActivity {
    ArrayList<Tremps> trempsList;
    List<String> VieList = new ArrayList<>();
    MyRides_Adapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        trempsList = new ArrayList<Tremps>(); //arrayList of tremps

       //refresh data from server on swipe



        ListView listview = (ListView)findViewById(R.id.list);




        adapter = new MyRides_Adapter(this, R.layout.activity_myrides, trempsList); //adapter will use the RoW layout

        listview.setAdapter(adapter); //set to the find ride xml the row_rides adapter

        new JSONAsyncTask().execute("http://somw3.ddns.net/user_tremps.php?" + "user_id=" +
                MainActivity.id); //go to this adress and fetch the query



        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new JSONAsyncTask().execute("http://somw3.ddns.net/user_tremps.php?" + "user_id="+
                        MainActivity.id); //go to this adress and fetch the query



                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            Intent intent = new Intent(Myrides_Activity.this, My_Passangers.class);
                intent.putExtra("tremp_id", trempsList.get(position).getTrempID());//Go to Tremps_Activity Activity
                startActivity(intent);

            }
        });


    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog; //the small window tha sows conecting to server/loading please wait
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Myrides_Activity.this);
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
                    JSONArray jarray = jsono.getJSONArray("user_tremps"); //we called the Json  in our php file Tremps

                    //Run threw the json file and create tremp objects.

                    for (int i = 0; i < jarray.length(); i++) {

                        JSONObject object = jarray.getJSONObject(i);

                        Tremps tremp = new Tremps();

                        tremp.setTrempID(object.getString("id"));
                        tremp.setTrempSource(object.getString("stop_name"));
                        tremp.setTrempDest(object.getString("stop_name_dest"));
                        tremp.setTrempOutTime(object.getString("out_time"));
                        tremp.setDate(object.getString("posted_at"));
                        tremp.setGender(object.getString("gender"));

                        if(!VieList.contains(object.getString("id"))) {

                            trempsList.add(tremp);


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

    }


