package com.example.matias.anda.views;


import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matias.anda.R;
import com.example.matias.anda.controllers.HttpPost;
import com.example.matias.anda.utilities.JsonHandler;
import com.example.matias.anda.utilities.SystemUtilities;

import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment implements  View.OnClickListener {

    String Key;
    Context context;
    private BroadcastReceiver br = null;
    String jsonobject;
    private final String URL_POST =
            "http://pliskin12.ddns.net:8080/taller-bd-11/usuarios/login";
    EditText et_nickname;
    EditText et_password;
    Button btn_login;
    CheckBox checkBox;

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
                    /** Chequear conexion a internet */
                    if(su.isNetworkAvailable()){
                        new HttpPost(getActivity().getApplicationContext(),
                                new HttpPost.AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            System.out.println("Estoy en el Login: " + output + "\n");
                            Key = output;
                        }
                    }).execute(URL_POST, jsonobject);

                        if(checkBox.isChecked())
                            rememberMe(et_nickname.getText().toString(), et_password.getText().toString());
                        getActivity().getFragmentManager().popBackStack();


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

    private void rememberMe(String user, String pass) {


    }

    private boolean validate() {
        if(et_nickname.getText().toString().trim().equals("") ||
                et_password.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
}
