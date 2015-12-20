package com.example.matias.anda.utilities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Matias on 19-12-2015.
 */
public class JsonHandler {

    String resultado;

    public  String getRegistro(String nombre, String apellido,
                               String username, String email,
                               String contraseña){

        try{
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




        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public  String getLogin(String nickname, String password){
        try{
            JSONObject json = new JSONObject();
            json.put("username",nickname);
            json.put("password",password);
            resultado = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  resultado;
    }
}
