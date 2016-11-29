package com.androidbelieve.materialnavigationdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class My_Passangers extends AppCompatActivity {
    ArrayList<Tremps> trempsList;
    List<String> VieList = new ArrayList<>();
    Passangers_Adapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout); //refresh data from server on swipe

        Intent intent = getIntent();
       final String tremp_id=intent.getStringExtra("tremp_id");


        trempsList = new ArrayList<Tremps>(); //arrayList of tremps

        //refresh data from server on swipe

        new JSONAsyncTask().execute("http://somw3.ddns.net/tremp_passengers.php?" + "tremp_id=" +
                tremp_id); //go to this adress and fetch the query

        System.out.println("http://somw3.ddns.net/tremp_passengers.php?" + "tremp_id=" +
                tremp_id);

        ListView listview = (ListView)findViewById(R.id.list);




        adapter = new Passangers_Adapter(this, R.layout.activity_my__passangers, trempsList); //adapter will use the RoW layout

        listview.setAdapter(adapter); //set to the find ride xml the row_rides adapter


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //when swiping the screeen
                new JSONAsyncTask().execute("http://somw3.ddns.net/tremp_passengers.php?" + "tremp_id=" +
                        tremp_id); //go to this adress and fetch the query

                mSwipeRefreshLayout.setRefreshing(false);

            }
        });



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                new JSonTask().execute("http://somw3.ddns.net/remove_user_from_tremp.php?" + "user_id=" +
                        trempsList.get(position).getTrempDriverId() + "&tremp_id=" +
                        trempsList.get(position).getTrempID());


                Intent intent = getIntent();
                finish();
                startActivity(intent);




            }
        });



    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog; //the small window tha sows conecting to server/loading please wait
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(My_Passangers.this);
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


                    JSONArray jarray = jsono.getJSONArray("users"); //we called the Json  in our php file Tremps

                    //Run threw the json file and create tremp objects.


                    for (int i = 0; i < jarray.length(); i++) {

                        JSONObject object = jarray.getJSONObject(i);

                        Tremps tremp = new Tremps();
                        tremp.setTrempID(object.getString("tremp_id"));
                        tremp.setTrempDriverId(object.getString("user_id"));
                        tremp.setTrempFname(object.getString("user_name"));
                        tremp.setTrempLname(object.getString("user_lname"));
                        tremp.setTrempPhone(object.getString("user_phone"));
                        //when refresh by swipe,we don'd want to see same tremps twice, or inactive tremps

                        if (!VieList.contains(object.getString("user_id"))) {  //don't dipaly same tremp twice...


                                trempsList.add(tremp);


                        }


                        VieList.add(object.getString("user_id"));



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
            dialog = new ProgressDialog(My_Passangers.this);
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
                Toast.makeText(getApplicationContext(), "Passanger Removed ", Toast.LENGTH_LONG).show();

        }
    }


}




