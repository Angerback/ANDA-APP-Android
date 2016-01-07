package com.example.matias.anda.views;


import android.app.Fragment;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.matias.anda.R;
import com.example.matias.anda.controllers.HttpGet;
import com.example.matias.anda.utilities.JsonHandler;
import com.example.matias.anda.utilities.SystemUtilities;

import java.util.List;

/**
 * Clase Fragmento que se utiliza para mostrar la lista de
 * los ultimos reportes
 */
public class ViewReports extends Fragment {


    Context context;
    String id;
    String auth_token;
    static String GET_REPORTES = "get_reportes";

    static final String URL_GET = "http://pliskin12.ddns.net:8080/taller-bd-11/reportes/rango/0/9";


    /** Constructor */
    public ViewReports(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_view_reports, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();


        String token_id = getArguments().getString("key");
        JsonHandler jh = new JsonHandler();
        id = jh.getValor(token_id, "idUsuario");
        auth_token = jh.getValor(token_id, "auth_token");



        //HttpGet getReports = new HttpGet(getActivity().getApplicationContext());




    }



}
