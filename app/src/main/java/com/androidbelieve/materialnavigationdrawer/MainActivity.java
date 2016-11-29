package com.androidbelieve.materialnavigationdrawer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbelieve.materialnavigationdrawer.Fragments.LogOut_Fragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity  extends Activity {

    public static String id,fname;

    //First screeen
    private static final String REGISTER_URL = "http://somw3.ddns.net/user_deatils.php";
    String result;


    private CallbackManager callbackManager; //facebook sdk element
    private TextView info;
    private LoginButton loginButton; //facebook sdk element


    String facebook_id,f_name, m_name, l_name, gender, profile_image, full_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //generate Hash key for facebook sdk,we don't need to understand this code !!!!
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.stas.project",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        //Use Facebook API verify the user,if you dint have facebook app installed on your phone you can't use the app
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {  // Facebook Log in button activities

            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();  //create profile object

             if (profile != null) {                  //get data from facebook grpah
                   facebook_id = profile.getId();
                   f_name = profile.getFirstName();

                    l_name = profile.getLastName();
                    full_name = profile.getName();




             }
//                }

                // Run Sql Query check if user already exists


                // on Successfull  login keep user's details on local storage of the phone
                //so next time user use the app it recognize him




                //use facebook graph get user's data
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {

                                gender = object.optString("gender"); //gender is a part of Graph and not profile object
                                //Pass data to Register_Activity Activity

                                facebook_id = object.optString("id");

                                f_name = object.optString("first_name");

                                l_name = object.optString("last_name");
                                full_name = object.optString("name");


                                fname = object.optString("name");
                                id = facebook_id;



                                System.out.println(gender);
                                System.out.println(facebook_id);
                                System.out.println(full_name);
                                System.out.println(f_name);
                                System.out.println(l_name);


                                SharedPreferences sharedPref = getSharedPreferences("mySettings", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                SharedPreferences sharedPref2 = getSharedPreferences("mySettings2", MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = sharedPref.edit();
                                editor.putString("mySetting", facebook_id); //keep facebook id localy
                                editor.putString("mySetting2", full_name);
                                editor.commit();


                                //After we get his details from facebook,user need to fill other fileds (phone)

                                Intent myIntent = new Intent(MainActivity.this, Register_Activity.class);
                                myIntent.putExtra("full_name", full_name);
                                myIntent.putExtra("f_name", f_name);
                                myIntent.putExtra("l_name", l_name);
                                myIntent.putExtra("id", facebook_id);
                                myIntent.putExtra("gender", gender);
                                // myIntent.putExtra("picture", profile_image);

                                MainActivity.this.startActivity(myIntent); // this command take us to register Activity

                            }
                        });
                //Bandle is a part of facebook grpah
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,gender,link,name,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

                info.setText("Login attempt cancelled.");
            } //Facebook

            @Override
            public void onError(FacebookException e) { //Facebook

                info.setText("Login attempt failed.");
            }
        });



    }


    public void onResume() {
        super.onResume();

        //Returning user   is logged in automaticly using the local stored data (shared preferences)
        SharedPreferences sharedPref = getSharedPreferences("mySettings", MODE_PRIVATE);
        String get = sharedPref.getString("mySetting", null); //get data from local store
        String get2 = sharedPref.getString("mySetting2", null); //get data from local store

        boolean flag= LogOut_Fragment.logout;


        if(flag){
            FacebookSdk.sdkInitialize(getApplicationContext());
            SharedPreferences preferences = getSharedPreferences("mySetting", 0);
            preferences.edit().remove("get").commit();
            SharedPreferences preferences2 = getSharedPreferences("mySetting2", 0);
            preferences.edit().remove("get2").commit();
            LoginManager.getInstance().logOut();

            get=null;
        }
        //if get is not empty,user is returned user.
        if (get != (null)) {

            id=get.toString();
            fname=get2.toString();

            //go to Start Activity
            Intent intent = new Intent(MainActivity.this, Main_Page.class);
            startActivity(intent);

        }

    }

    private void register(String facebook_id) {

        String urlSuffix = "?user_id="+facebook_id;


        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait",null, true, true);
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


                    result = bufferedReader.readLine();

                   
                    return result;

                }catch(Exception e){
                    return null;
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



}
