package com.androidbelieve.materialnavigationdrawer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidbelieve.materialnavigationdrawer.R;

/**
 * Created by Ratan on 7/9/2015.
 */
public class Empty_fragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.my_details_fragment,null);


    }
}
