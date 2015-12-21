package com.example.matias.anda.views;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.matias.anda.R;
import com.example.matias.anda.utilities.JsonHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewReport extends Fragment implements View.OnClickListener  {

    String URL_POST = "";
    Button btn_ok;
    EditText et_contenido;
    EditText et_foto;

    public NewReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_new_report, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();

        String URL= "http://dns.net:8080/taller-bd-11/usuarios/";


        // Obtener el key enviado desde la clase Reports
        String key = getArguments().getString("key");
        System.out.println("newreports :"+key);


        JsonHandler jh = new JsonHandler();
        String id = jh.getValor(key,"idUsuario");
        String auth_token = jh.getValor(key,"auth_token");
        URL_POST = URL_POST.concat(URL).concat(id).concat("/").concat("reportes");
        System.out.println(URL_POST);
        System.out.println("auth_token es: "+auth_token);
        System.out.println("OK\n");

        et_contenido = (EditText)getView().findViewById(R.id.contenido_newreport);
        et_foto = (EditText)getView().findViewById(R.id.foto_newreport);
        btn_ok = (Button)getView().findViewById(R.id.btn_ok_newreport);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (getId()){
            case R.id.btn_ok_newreport:
                System.out.println("POST\n");
                break;
        }
    }
}
