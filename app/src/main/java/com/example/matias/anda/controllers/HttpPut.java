package com.example.matias.anda.controllers;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Matias on 15-01-2016.
 */
public class HttpPut extends AsyncTask<String, Void, String>{


    private Context context;
    public AsyncResponse delegate = null;

    public HttpPut(Context context, AsyncResponse delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    public interface  AsyncResponse{
        void processFinish(String output);
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(100000);
            connection.setConnectTimeout(100000);
            connection.setRequestProperty("auth_token", params[2]);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("PUT");
            connection.setDoInput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);

            // Send request
            DataOutputStream os = new DataOutputStream(
                    connection.getOutputStream());
            os.writeBytes(params[1]);
            os.flush();
            os.close();

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
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  "null";

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        delegate.processFinish(s);


    }
}





