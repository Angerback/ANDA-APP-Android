package com.example.matias.anda.views;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matias.anda.R;
import com.example.matias.anda.utilities.JsonHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditReport extends Fragment {


    String id;
    String auth_token;


    public EditReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_edit_report, container, false);

            String str1 = getArguments().getString("contenido1");

        System.out.println("edit " + str1);
        System.out.println("edit " + getArguments().getString("imagen1"));
        System.out.println("edit " + getArguments().getString("latitud1"));
        System.out.println("edit " + getArguments().getString("longitud1"));
        System.out.println("edit " + getArguments().getString("iduniversidad1"));
        System.out.println("edit " + getArguments().getString("fecha1"));
        System.out.println("edit " + getArguments().getString("idreporte1"));
        System.out.println("edit " + getArguments().getString("idusuario1"));
        return v;
    }


    @Override
    public void onResume() {
super.onResume();

/*
        String key = getArguments().getString("key");
        JsonHandler jh = new JsonHandler();
        id = jh.getValor(key, "idUsuario");
        auth_token = jh.getValor(key, "auth_token");
*/


    }
}
