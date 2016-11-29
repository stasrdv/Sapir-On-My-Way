package com.androidbelieve.materialnavigationdrawer.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidbelieve.materialnavigationdrawer.Post_Activity;
import com.androidbelieve.materialnavigationdrawer.R;

/**
 * Created by Ratan on 7/9/2015.
 */
public class Post_Ride_fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_post, container, false);
        Intent intent = new Intent(getActivity(), Post_Activity.class);//sends us to activirt
        startActivity(intent);
        return inflater.inflate(R.layout.fragment_post,null);
    }
}
