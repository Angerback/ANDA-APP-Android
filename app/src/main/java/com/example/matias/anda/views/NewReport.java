package com.example.matias.anda.views;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matias.anda.R;
import com.example.matias.anda.controllers.HttpPost;
import com.example.matias.anda.utilities.JsonHandler;
import com.example.matias.anda.utilities.SystemUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewReport extends Fragment implements View.OnClickListener  {

    String id;
    Context context;
    String URL_POST = "";
    Button btn_ok;
    String auth_token;
    EditText et_contenido;
    EditText et_foto;
    String jsonobject;

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

        String URL= "http://pliskin12.ddns.net:8080/taller-bd-11/usuarios/";


        // Obtener el key enviado desde la clase Reports
        String key = getArguments().getString("key");
        System.out.println("newreports :"+key);


        JsonHandler jh = new JsonHandler();
        id = jh.getValor(key,"idUsuario");
        auth_token = jh.getValor(key,"auth_token");
        URL_POST = URL_POST.concat(URL).concat(id).concat("/").concat("reportes");
        System.out.println(URL_POST);
        System.out.println("auth_token es: "+auth_token);


        et_contenido = (EditText)getView().findViewById(R.id.contenido_newreport);
        et_foto = (EditText)getView().findViewById(R.id.foto_newreport);
        btn_ok = (Button)getView().findViewById(R.id.btn_ok_newreport);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok_newreport:
                JsonHandler jsonHandler = new JsonHandler();
                jsonobject = jsonHandler.getNewReport(et_contenido.getText().toString(),
                        et_foto.getText().toString(),id);
                SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());

                if (validate()){
                    if(su.isNetworkAvailable()){
                        new HttpPost(getActivity().getApplicationContext(),
                                new HttpPost.AsyncResponse(){

                            @Override
                            public void processFinish(String output) {
                                System.out.println("salida new Report:"+ output + "\n");
                                getActivity().getFragmentManager().popBackStack();
                            }
                        }).execute(URL_POST,jsonobject,auth_token);
                    }//network-available
                    else{
                        Toast toast = Toast.makeText(this.context,"NO HAY CONEXION A INTERNET",
                                Toast.LENGTH_LONG);
                        toast.show();
                    }


                } //if-validate
                else{
                    Toast toast = Toast.makeText(this.context, "Ingrese los datos",
                            Toast.LENGTH_LONG);
                    toast.show();
                }

                break;
        }
    }// onClick

    private boolean validate() {

        if(et_contenido.getText().toString().trim().equals("") ||
                et_foto.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
}
