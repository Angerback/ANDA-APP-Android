package com.example.matias.anda.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.matias.anda.utilities.JsonHandler;
import com.example.matias.anda.views.NewReport;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Handler;

/**
 * Created by Matias on 30-12-2015.
 */
public class HttpGet extends AsyncTask<String, Void, String> {


    private Context context;
    protected  TaskResult delegate = null;


    public HttpGet(Context context, TaskResult delegate) {

        this.context = context;
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestProperty("auth_token", params[1]);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();

            System.out.println("get Responsive: "+ sb.toString());
            return sb.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String resultado) {
        super.onPostExecute(resultado);
        delegate.onSuccess(resultado);
    }

    /** Interfaz para setear el resultado */
    public   interface TaskResult{
        void onSuccess(String result);
    }
}
