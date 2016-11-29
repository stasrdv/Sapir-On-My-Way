package com.androidbelieve.materialnavigationdrawer.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidbelieve.materialnavigationdrawer.MainActivity;
import com.androidbelieve.materialnavigationdrawer.Main_Page;
import com.androidbelieve.materialnavigationdrawer.R;


public class LogOut_Fragment  extends android.support.v4.app.Fragment {
    public static boolean    logout;

@Nullable
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        logout=true;
        Intent intent = new Intent(getActivity(), MainActivity.class);//sends us to activirt
        startActivity(intent);
        return inflater.inflate(R.layout.fragment_find,null);


}
        }
