package com.example.matias.anda.views;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matias.anda.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ReportDetail extends Fragment {

    ImageView foto;

    public ReportDetail(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_detail, container, false);
        Bundle bundle = getArguments();

        ((TextView) v.findViewById(R.id.report_detail_primero)).setText(bundle.getString("contenido"));

    return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        String key = getArguments().getString("key");

        foto = (ImageView) getView().findViewById(R.id.foto_report_detail);

        /** muestra la imagen en el imageview*/
        Picasso.with(this.getActivity()).load(getArguments().getString("imagen")).into(foto);

    }

}
