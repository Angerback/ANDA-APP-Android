package com.example.matias.anda.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;

import com.cloudinary.Cloudinary;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias on 26-12-2015.
 */
public class UploadCouldinary extends AsyncTask<String,Void,String> {

    Map<String,String> Result;
    String resultado;
    private Context context;
    Cloudinary cloudinary;
    public  AsyncResponse delegate = null;
    Handler handler;

    public interface  AsyncResponse{
        void processFinish(String output);
    }

    public UploadCouldinary(Context context, AsyncResponse delegate, Handler handler1) {
        this.handler = handler1;
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Map config = new HashMap();
        config.put("cloud_name", "anda");
        config.put("api_key", "699621158764738");
        config.put("api_secret", "s5uLsD8AqE_dAgJNcY7zYkVAT80");
        cloudinary = new Cloudinary(config);
    }

    @Override
    protected String doInBackground(String... params) {


        try {
            Result =   cloudinary.uploader().upload(params[0], new HashMap());
             resultado = Result.get("url");

        } catch (IOException e) {
            e.printStackTrace();
        }


        return resultado;
    }


    @Override
    protected void onPostExecute(String s) {
        handler.sendEmptyMessage(0);
        super.onPostExecute(s);
        delegate.processFinish(s);
    }
}
