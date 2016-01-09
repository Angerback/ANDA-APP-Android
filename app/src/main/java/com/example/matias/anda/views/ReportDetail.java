package com.example.matias.anda.views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matias.anda.R;

/**
 * Created by Lalan on 08-01-2016.
 */
public class ReportDetail extends Fragment {

    public ReportDetail(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_detail, container, false);
        //Bundle bundle = getArguments();

        //((TextView) v.findViewById(R.id.report_detail_primero)).setText(bundle.getString("item"));
        //((TextView) v.findViewById(R.id.report_detail_segundo)).setText(bundle.getStringArray("data")[1]);

    return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        String key = getArguments().getString("key");

    }
}
