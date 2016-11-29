package com.androidbelieve.materialnavigationdrawer.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidbelieve.materialnavigationdrawer.MapsActivity;
import com.androidbelieve.materialnavigationdrawer.R;



public class Location  extends android.support.v4.app.Fragment {




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        Intent intent = new Intent(getActivity(), MapsActivity.class);//sends us to activirt
        startActivity(intent);

        return inflater.inflate(R.layout.fragment_location,null);


    }
}

