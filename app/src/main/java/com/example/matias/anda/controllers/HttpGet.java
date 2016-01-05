package com.example.matias.anda.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Matias on 30-12-2015.
 */
public class HttpGet extends AsyncTask<String, Void, String> {


    private Context context;

    public HttpGet(Context context) {
        this.context = context;
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

            System.out.println("AQUI");

            // Get resposive
  /*          BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();
            System.out.println("Responsive :" + response.toString() + "\n");

            return  response.toString();*/

/*
            InputStream in = new BufferedInputStream(connection.getInputStream());
            int data = in.read();
            while (data != -1) {
                char current = (char) data;
                data = in.read();
                System.out.print(current);
                System.out.println("CACA");
            }
*/

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            return sb.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}
