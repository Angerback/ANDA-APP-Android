package com.example.matias.anda.views;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.matias.anda.R;
import com.example.matias.anda.controllers.HttpPut;
import com.example.matias.anda.utilities.JsonHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditReport extends Fragment implements View.OnClickListener{


    String id;
    String auth_token;
    EditText edit_contenido;
    Button edit_ok;
    String URL_PUT =   "http://pliskin12.ddns.net:8080/taller-bd-11/reportes/edit/";


    public EditReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_edit_report, container, false);
        auth_token = getArguments().getString("key");
        String id_reporte = (String) getArguments().get("idreporte1");
        URL_PUT = URL_PUT + id_reporte;


/*        String str1 = getArguments().getString("contenido1");
        System.out.println("edit " + str1);
        System.out.println("edit " + getArguments().getString("imagen1"));
        System.out.println("edit " + getArguments().getString("latitud1"));
        System.out.println("edit " + getArguments().getString("longitud1"));
        System.out.println("edit " + getArguments().getString("iduniversidad1"));
        System.out.println("edit " + getArguments().getString("fecha1"));
        System.out.println("edit " + getArguments().getString("idreporte1"));
        System.out.println("edit " + getArguments().getString("idusuario1"));*/


        edit_ok = (Button)v.findViewById(R.id.btn_edit_ok);
        edit_ok.setOnClickListener(this);
        return v;
    }


    @Override
    public void onResume() {
super.onResume();

        edit_contenido = (EditText) getView().findViewById(R.id.et_edit_contenido);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_edit_ok:
                System.out.println("ENTROO LA MIERDA ?? ");

                String json;
                JsonHandler jsonHandler = new JsonHandler();
                json = jsonHandler.editReport(edit_contenido.getText().toString(),getArguments().getString("imagen1"),
                        getArguments().getString("idusuario1"),getArguments().getString("latitud1"),
                        getArguments().getString("longitud1"),getArguments().getString("iduniversidad1"),
                        getArguments().getString("fecha1"));


                new HttpPut(getActivity().getApplicationContext(), new HttpPut.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        System.out.println(output);
                    }
                }).execute(URL_PUT,json,auth_token);




                break;

        }


    }
}
