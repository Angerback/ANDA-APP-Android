package com.example.matias.anda.views;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.matias.anda.R;
import com.example.matias.anda.controllers.HttpPost;
import com.example.matias.anda.utilities.JsonHandler;
import com.example.matias.anda.utilities.SystemUtilities;


public class Register extends Fragment implements  View.OnClickListener {

    Context context;
    private  BroadcastReceiver br = null;
    String jsonobject;
    private final String URL_POST =
            "http://pliskin12.ddns.net:8080/taller-bd-11/usuarios/registro";
    EditText et_name;
    EditText et_lastname;
    EditText et_nickname;
    EditText et_email;
    EditText et_password;
    Button btn_register;


    public Register() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register,container,false);

        return view;
    }



    @Override
    public void onResume(){

        et_name = (EditText)getView().findViewById(R.id.etName_r);
        et_lastname = (EditText)getView().findViewById(R.id.etApellido_r);
        et_nickname = (EditText)getView().findViewById(R.id.etNickname_r);
        et_email = (EditText)getView().findViewById(R.id.etEmail_r);
        et_password = (EditText)getView().findViewById(R.id.etPassword_r);
        btn_register = (Button)getView().findViewById(R.id.btn_registro);
        btn_register.setOnClickListener(this);
        super.onResume();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registro:

                JsonHandler jsonHandler = new JsonHandler();
                jsonobject = jsonHandler.getRegistro(et_name.getText().toString(),
                        et_lastname.getText().toString(),
                        et_nickname.getText().toString(),
                        et_email.getText().toString(),
                        et_password.getText().toString());
                SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());

                /** Validar los campos */
                if(validate()){
                    /** Chequear conexion a internet */
                    if(su.isNetworkAvailable()) {
                        new HttpPost(getActivity().getApplicationContext(),
                                new HttpPost.AsyncResponse() {
                            @Override
                            public void processFinish(String output) {

                                System.out.println("Estoy en el Register: " + output + "\n");
                                getActivity().getFragmentManager().popBackStack();
                                ((InputMethodManager) getActivity().getSystemService
                                        (Activity.INPUT_METHOD_SERVICE)).toggleSoftInput(
                                        InputMethodManager.SHOW_IMPLICIT, 0);
                            }
                        }).execute(URL_POST, jsonobject);
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

    /** Método que valida que se halla ingresado
     por lo menos el nicname el mail y la contraseña */
    private boolean validate() {
        if(et_nickname.getText().toString().trim().equals("") ||
                et_email.getText().toString().trim().equals("") ||
                et_password.getText().toString().trim().equals(""))
            return false;
        else
            return true;

    }

}
