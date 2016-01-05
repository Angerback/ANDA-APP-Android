package com.example.matias.anda.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    public String getNewReport(String contenido, String foto, String id, String latitud, String longitud){
        String resultado;

        // Formatear la fecha
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(c.getTime());

        try {
            JSONObject json = new JSONObject();
            json.put("contenido",contenido);
            json.put("fecha",formattedDate);
            json.put("foto",foto);
            json.put("latitud",latitud);
            json.put("longitud",longitud);
            json.put("idUniversidad","20");
            json.put("idUsuario",id);
            json.put("solucionado","0");
            json.put("validado","0");
            json.put("visible","1");
            resultado = json.toString();
            return resultado;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public String[] getReportes(String reportes){

        try {
            JSONArray ja = new JSONArray(reportes);
            if(ja == null){
                return  null;
            }
            String[] result = new String[ja.length()];
            String reporte;
            for (int i = 0; i< ja.length();i++){
                JSONObject row = ja.getJSONObject(i);
                reporte = " " + row.getString("idUuario") + " " + row.getString("fecha");
                result[i] = reporte;
            }
            return  result;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;

    }
}
