package com.example.matias.anda.views;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matias.anda.R;
import com.example.matias.anda.controllers.HttpGet;
import com.example.matias.anda.utilities.JsonHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyReports extends Fragment {

    Context context;
    String URL_GET =
            "http://pliskin12.ddns.net:8080/taller-bd-11/usuarios/";
    String id;
    String auth_token;

    public MyReports() {
        // Required empty public constructor
    }

    public MyReports(Context context) {
        this.context = context;
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
        // Obtener el key(id + autenticacion) desde el Bundle
        String key = getArguments().getString("key");
        // Obtener el id del usuario
        JsonHandler jh = new JsonHandler();
        id = jh.getValor(key, "idUsuario");
        // Obtener la autenticación
        auth_token = jh.getValor(key, "auth_token");

        URL_GET = URL_GET+id+"/reportes";

        new HttpGet(getActivity().getApplicationContext(), new HttpGet.TaskResult() {
            @Override
            public void onSuccess(String result) {
                System.out.println("MR == " +result );
                manejarMisReportes(result);

            }
        }).execute(URL_GET, auth_token);
    }

    private void manejarMisReportes(String result) {



    }
}
