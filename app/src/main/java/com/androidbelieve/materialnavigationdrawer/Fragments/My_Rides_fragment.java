package com.androidbelieve.materialnavigationdrawer.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidbelieve.materialnavigationdrawer.Myrides_Activity;
import com.androidbelieve.materialnavigationdrawer.R;

public class My_Rides_fragment extends android.support.v4.app.Fragment {
    public static boolean    logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        Intent intent = new Intent(getActivity(), Myrides_Activity.class);//sends us to activirt
        startActivity(intent);
        return inflater.inflate(R.layout.fragment_find,null);


    }
}
