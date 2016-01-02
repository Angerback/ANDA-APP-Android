package com.example.matias.anda.views;


import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.matias.anda.R;
import com.example.matias.anda.Reports;
import com.example.matias.anda.controllers.HttpPost;
import com.example.matias.anda.utilities.JsonHandler;
import com.example.matias.anda.utilities.SystemUtilities;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment implements  View.OnClickListener {



    Context context;
    String jsonobject;
    private final String URL_POST =
            "http://pliskin12.ddns.net:8080/taller-bd-11/usuarios/login";
    EditText et_nickname;
    EditText et_password;
    Button btn_login;
    CheckBox checkBox;
    ProgressDialog pDialog;


    public Login() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        return  view;
    }

    @Override
    public void onResume(){

        et_nickname = (EditText)getView().findViewById(R.id.et_login_nick);
        et_password = (EditText)getView().findViewById(R.id.et_login_pass);
        btn_login = (Button)getView().findViewById(R.id.btn_login_iniciarsesion);
        checkBox = (CheckBox)getView().findViewById(R.id.cb_remember);
        btn_login.setOnClickListener(this);
        super.onResume();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_login_iniciarsesion:


                JsonHandler jsonHandler = new JsonHandler();
                jsonobject = jsonHandler.getLogin(et_nickname.getText().toString(),
                        et_password.getText().toString());
                SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());

                /** Validar los campos */
                if(validate()){
                    pDialog = new ProgressDialog(getActivity());
                    pDialog.setMessage("Iniciando sesión...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    /** Chequear conexion a internet */
                    if(su.isNetworkAvailable()){

                        new HttpPost(getActivity().getApplicationContext(),
                                new HttpPost.AsyncResponse() {
                        @Override
                        public void processFinish(String output) {

                            pDialog.dismiss();
                            if(output == "error_responsive"){
                                //Usuario o contraseña incorrecta
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Usuario o Contraseña Incorrecta",
                                        Toast.LENGTH_LONG);
                                toast.show();
                            }

                            else{
                                // Intent para ir a la actividad reportes una vez el login sea satisfactorio
                                final Intent intent = new Intent(getActivity().getBaseContext(),
                                        Reports.class);
                                intent.putExtra("auth_key", output);
                                getActivity().startActivity(intent);
                            }

                        }}).execute(URL_POST, jsonobject,"False");

                    }


                    else{
                        Toast toast = Toast.makeText(this.context,"NO HAY CONEXION A INTERNET",
                                Toast.LENGTH_LONG);
                        toast.show();
                    }


                }
                else{
                    Toast toast = Toast.makeText(this.context, "Ingrese los datos",
                            Toast.LENGTH_LONG);
                    toast.show();
                }
                break;

        }

    }


    private boolean validate() {
        if(et_nickname.getText().toString().trim().equals("") ||
                et_password.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }





}
