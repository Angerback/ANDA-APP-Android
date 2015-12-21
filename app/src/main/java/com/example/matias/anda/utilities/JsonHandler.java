package com.example.matias.anda.utilities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Matias on 19-12-2015.
 */
public class JsonHandler {



    public  String getRegistro(String nombre, String apellido,
                               String username, String email,
                               String contraseña){

        try{
            String resultado;
            JSONObject json = new JSONObject();
            json.put("nombreUsuario", nombre);
            json.put("apellidoUsuario", apellido);
            json.put("nickname", username);
            json.put("password",contraseña);
            json.put("email",email);
            json.put("status",1 );
            json.put("validacion",0 );
            json.put("curador", 0);
            resultado = json.toString();
            return resultado;

        } catch (JSONException e) {
            e.printStackTrace();
        }
            return null;

    }

    public  String getLogin(String nickname, String password){
        try{
            String resultado;
            JSONObject json = new JSONObject();
            json.put("username",nickname);
            json.put("password",password);
            resultado = json.toString();
            return resultado;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;


    }

    public String getValor(String json, String name) {

        String resultado;
        try {
            JSONObject jsonObject = new JSONObject(json);
            resultado = jsonObject.get(name).toString();
            return resultado;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
