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
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Register_Activity extends ActionBarActivity {

    //vars declare
    EditText fname,lname,_phone;
    //ProfilePictureView profilePictureView;
    Button register;
    String f_name,l_name,gender,facebook_id,smoking;
    Bitmap bitmap;
    ImageView img;
    CheckBox smoke;



    //host Adress
    private static final String REGISTER_URL = "http://somw3.ddns.net/register.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();  //get data from another activity
        setContentView(R.layout.activity_register);
        smoke=(CheckBox)findViewById(R.id.checkBox); //checkbox
        smoke.setOnClickListener(checkboxClickListener);




        //display to user his data
        f_name = intent.getStringExtra("f_name");
        l_name = intent.getStringExtra("l_name");
        gender = intent.getStringExtra("gender");
        facebook_id=intent.getStringExtra("id");


        //fill the text boxes
        _phone=(EditText) findViewById(R.id.phone);
        fname = (EditText) findViewById(R.id.name);   //
        fname.setText(f_name);
        fname.setEnabled(false);
        lname = (EditText) findViewById(R.id.lname);   //
        lname.setText(l_name);
        lname.setEnabled(false);

        img = (ImageView)findViewById(R.id.image);

        new LoadImage().execute("https://graph.facebook.com/" + MainActivity.id + "/picture?height=400&width=400&migration_overrides=%7Boctober_2012%3Atrue%7D");


        register = (Button) findViewById(R.id.register);

        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ( _phone.getText().length()==10) {
                    register(facebook_id, f_name, l_name, _phone.getText().toString(), gender,smoking);
                    Intent back = new Intent(Register_Activity.this, Main_Page.class);
                    startActivity(back);
                }

            }
        };

        register.setOnClickListener(oclBtnOk);


    }

    View.OnClickListener checkboxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean checked = ((CheckBox) view).isChecked();
            if (checked) {
                smoking ="1";

            }
            else{
                smoking ="0";
            }
        }};



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "Please Enter Your Phone Number",
                    Toast.LENGTH_LONG).show();

        return false;
        // Disable back button..............
    }

    // Send the data to php page
    private void register(String facebook_id, String f_name,String lname, String phone,String gender,String smoke) {
        String urlSuffix = "?user_id="+facebook_id+"&user_name="+f_name+"&user_lname="+lname+"&user_phone="+phone+
                "&gender="+gender
                +"&smoke="+smoke;


        System.out.println(urlSuffix);


        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Register_Activity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;
                    result = bufferedReader.readLine();

                    System.out.println(url);
                    return result;
                }catch(Exception e){
                    return null;
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }


    class JSonTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog; //the small window tha sows conecting to server/loading please wait
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Register_Activity.this);
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