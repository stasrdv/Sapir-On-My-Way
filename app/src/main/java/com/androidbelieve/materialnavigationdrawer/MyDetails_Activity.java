package com.androidbelieve.materialnavigationdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MyDetails_Activity extends AppCompatActivity {

    //vars declare
    EditText fname,lname,phone;
    ProfilePictureView profilePictureView;
    Button register;
    Bitmap bitmap;
    ImageView img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details_);


        SharedPreferences sharedPref = getSharedPreferences("mySettings", MODE_PRIVATE);
        String facebook_id = sharedPref.getString("mySetting", null); //get data from local store
        String full_name = sharedPref.getString("mySetting2", null); //get data from local store





        img = (ImageView)findViewById(R.id.image);

        new LoadImage().execute("https://graph.facebook.com/"+MainActivity.id+"/picture?height=400&width=400&migration_overrides=%7Boctober_2012%3Atrue%7D");

        phone = (EditText) findViewById(R.id.phone);

        fname = (EditText) findViewById(R.id.name);   //
        fname.setText(full_name);
        fname.setEnabled(false);


        Button register = (Button) findViewById(R.id.updateBtn);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if ( phone.getText().length()==10) {
                    new JSonTask().execute("http://somw3.ddns.net/user_details_update.php?" + "user_id=" + MainActivity.id
                                    + "&new_phone=" + phone.getText().toString()
                    );

                    System.out.println("http://somw3.ddns.net/user_details_update.php?" + "user_id=" + MainActivity.id
                            + "&new_phone=" + phone.getText().toString());


                    Intent back = new Intent(MyDetails_Activity.this, Main_Page.class);
                    startActivity(back);
                }
            }
        });





    }
    private  void checkFieldsForEmptyValues(){
        Button b = (Button) findViewById(R.id.updateBtn);

        String s1 = phone.getText().toString();


        if (s1.length() > 0) {
            b.setEnabled(true);
        } else {
            b.setEnabled(false);
        }

    }


    class JSonTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog; //the small window tha sows conecting to server/loading please wait
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MyDetails_Activity.this);
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
            //adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "Phone Updated", Toast.LENGTH_LONG).show();

        }
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){


                img.setImageBitmap(getCircleBitmap(image));


            }else{

                // pDialog.dismiss();
                // Toast.makeText(Drawable_menu_Adapter.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }

        private Bitmap getCircleBitmap(Bitmap bitmap) {
            final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);

            final int color = Color.RED;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawOval(rectF, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            bitmap.recycle();

            return output;
        }

    }


}








