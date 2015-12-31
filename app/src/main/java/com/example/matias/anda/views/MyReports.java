package com.example.matias.anda.views;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matias.anda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyReports extends Fragment {

    Context context;

    public MyReports() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_reports, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
/*        String key = getArguments().getString("key");
        System.out.println("MyReport: KEY= "+key);*/
    }
}
