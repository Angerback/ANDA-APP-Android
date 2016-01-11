package com.example.matias.anda.views;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matias.anda.R;
import com.example.matias.anda.utilities.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ReportDetail extends Fragment implements OnMapReadyCallback {

    ImageView foto;

    /**variables para el mapa*/
    double latitud;
    double longitud;
    MapView mMapView;
    private GoogleMap googleMap;

    /**#####################################################################################################*/
    public ReportDetail(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_detail, container, false);
        Bundle bundle = getArguments();

        ((TextView) v.findViewById(R.id.report_detail_primero)).setText(bundle.getString("contenido"));

        mMapView = (MapView) v.findViewById(R.id.mapa_report_detail);
        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(this);

    return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        latitud = Double.parseDouble(getArguments().getString("latitud"));
        longitud = Double.parseDouble(getArguments().getString("longitud"));
        /**se asigna longitud y latitud a una variable para establecer la posicion posteriormente*/
        LatLng posicion = new LatLng(latitud,longitud);
        /**se asigna el tipo de mapa*/
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        /**se muestra la marca en la posicion determinada*/
        googleMap.addMarker(new MarkerOptions().position(posicion));
                //icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        String key = getArguments().getString("key");

        /**imagen*/
        foto = (ImageView) getView().findViewById(R.id.foto_report_detail);
        /** muestra la imagen en el imageview*/
        Picasso.with(this.getActivity()).load(getArguments().getString("imagen")).into(foto);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
