package com.androidbelieve.materialnavigationdrawer.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidbelieve.materialnavigationdrawer.Find_Activity;
import com.androidbelieve.materialnavigationdrawer.MyDetails_Activity;
import com.androidbelieve.materialnavigationdrawer.R;

/**
 * Created by Ratan on 7/9/2015.
 */
public class MY_details_Fragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Intent intent = new Intent(getActivity(), MyDetails_Activity.class);//sends us to activirt
        startActivity(intent);
        return inflater.inflate(R.layout.fragment_find,null);


    }
}
