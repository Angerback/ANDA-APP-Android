package com.example.matias.anda.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Matias on 19-12-2015.
 */
public class HttpPost extends AsyncTask<String,Void,String>{

    private Context context;
    public  AsyncResponse delegate = null;



    public interface  AsyncResponse{
        void processFinish(String output);
    }

    public HttpPost(Context context, AsyncResponse delegate) {
        this.context = context;
        this.delegate = delegate;

    }

    @Override
    protected String doInBackground(String... params) {

        try {

            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(100000);
            connection.setConnectTimeout(100000);

            if(params[2] != "False"){
                // Si los parametros vienen con un header.
                //FORMA 1//connection.setRequestProperty("Authorization", "auth_token:" + params[2]);


                //FORMA 2
/*               String userCredentials  = "auth_token:"+params[2];
                byte[] encodedBytes = Base64.encode(userCredentials.getBytes(),0);
                userCredentials = "Basic " + encodedBytes;
                connection.setRequestProperty("Authorization",userCredentials);*/

                //FORMA 3
                 connection.setRequestProperty("auth_token",params[2]);
                // token
                System.out.println(params[2]);
                // json
                System.out.println(params[1]);

            }

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);


            // Send request
            DataOutputStream os = new DataOutputStream(
                    connection.getOutputStream());
            // Params[1] contiene el objeto json
            os.writeBytes(params[1]);
            os.flush();
            os.close();

            System.out.println("GG");
            int status = connection.getResponseCode();
            if(status >= 400)
                return "error_responsive";

            // Get resposive
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();
            System.out.println("Responsive :" +response.toString() +"\n");

            return response.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            Log.d("InputStream", e.getLocalizedMessage());
            return  "no_server";
        }
        return  "null";
    }

    @Override
    protected void onPostExecute(String resultado) {
        super.onPostExecute(resultado);

        if(resultado == "error_responsive"){
            Toast toast = Toast.makeText(this.context, "ERROR RESPONSIVE",
                    Toast.LENGTH_LONG);
            toast.show();
        }
        else if(resultado == "no_server"){
            Toast toast = Toast.makeText(this.context,
                    "No se puede alcanzar el servidor",
                    Toast.LENGTH_LONG);
            toast.show();
        }
        else if(resultado == "null"){
            Toast toast = Toast.makeText(this.context,"ERROR",
                    Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(this.context, "OPERACION EXITOSA",
                    Toast.LENGTH_LONG);
            toast.show();
        }

        delegate.processFinish(resultado);
    }
}
