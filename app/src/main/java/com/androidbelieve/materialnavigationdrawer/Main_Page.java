package com.androidbelieve.materialnavigationdrawer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.androidbelieve.materialnavigationdrawer.Fragments.Empty_fragment;
import com.facebook.login.widget.LoginButton;


public class Main_Page extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    String navTitles[];
    TypedArray navIcons;
    RecyclerView.Adapter recyclerViewAdapter;
    ActionBarDrawerToggle drawerToggle;
    private LoginButton loginButton;

    ImageView post,find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);



        //Let's first set up toolbar
        setupToolbar();

        //Initialize Views
        recyclerView  = (RecyclerView) findViewById(R.id.recyclerView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerMainActivity);

        //Setup Titles and Icons of Navigation Drawer
        navTitles = getResources().getStringArray(R.array.navDrawerItems);
        navIcons = getResources().obtainTypedArray(R.array.navDrawerIcons);


        /**
        *Here , pass the titles and icons array to the adapter .
        *Additionally , pass the context of 'this' activity .
        *So that , later we can use the fragmentManager of this activity to add/replace fragments.
        */

        recyclerViewAdapter = new Drawable_menu_Adapter(navTitles,navIcons,this);
        recyclerView.setAdapter(recyclerViewAdapter);

        /**
        *It is must to set a Layout Manager For Recycler View
        *As per docs ,
        *RecyclerView allows client code to provide custom layout arrangements for child views.
        *These arrangements are controlled by the RecyclerView.LayoutManager.
        *A LayoutManager must be provided for RecyclerView to function.
         */
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Finally setup ActionBarDrawerToggle
        setupDrawerToggle();


        //Add the Very First i.e Squad Fragment to the Container
        Fragment squadFragment = new Empty_fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerView,squadFragment,null);
        fragmentTransaction.commit();


         post = (ImageView) findViewById(R.id.post);
         find = (ImageView) findViewById(R.id.find);

        post.setClickable(true);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 400 milliseconds
                vib.vibrate(100);
                post.setEnabled(false);


                Intent intent = new Intent(Main_Page.this, Post_Activity.class); //Go to Bus Activity

                startActivity(intent);

            }
        });

        find.setClickable(true);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(100);

                find.setEnabled(false);
                Intent intent = new Intent(Main_Page.this, Find_Activity.class); //Go to Bus Activity

                startActivity(intent);

            }
        });

    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        drawerToggle.syncState();
    }

    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();



        post.setEnabled(true);
        find.setEnabled(true);


    }



    public void onResume() {


        super.onResume();
        post.setEnabled(true);
        find.setEnabled(true);


    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("")
                .setMessage("האם את בטוח שברצונך לסגור את היישום ?")
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("לא", null).show();
    }


}
