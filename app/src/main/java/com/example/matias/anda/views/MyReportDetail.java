package com.example.matias.anda.views;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matias.anda.R;
import com.example.matias.anda.utilities.JsonHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

/**
 * Created by Lalan on 11-01-2016.
 */
public class MyReportDetail extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    ImageView foto;
    Button boton_editar;
    String contenido;
    String auth_token;


    /**
     * variables para el mapa
     */
    double latitud;
    double longitud;
    MapView mMapView;
    private GoogleMap googleMap;

    public MyReportDetail() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_report_detail, container, false);
        Bundle bundle = getArguments();

        contenido = bundle.getString("contenido");
        ((TextView) v.findViewById(R.id.contenido_my_report_detail)).setText(contenido);

        mMapView = (MapView) v.findViewById(R.id.mapView_my_report_detail);
        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(this);

        // Obtener el auth_token
        auth_token  = getArguments().getString("key");




        return v;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;


        latitud = Double.parseDouble(getArguments().getString("latitud"));
        longitud = Double.parseDouble(getArguments().getString("longitud"));
        /**se asigna longitud y latitud a una variable para establecer la posicion posteriormente*/
        LatLng posicion = new LatLng(latitud, longitud);
        /**se asigna el tipo de mapa*/
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        /**se muestra la marca en la posicion determinada*/
        googleMap.addMarker(new MarkerOptions().position(posicion)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(posicion).zoom(18).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        boton_editar = (Button) getView().findViewById(R.id.btn_editar);
        boton_editar.setOnClickListener(this);
        /**imagen*/
        foto = (ImageView) getView().findViewById(R.id.foto_my_report_detail);
        /** muestra la imagen en el imageview*/
        Picasso.with(this.getActivity()).load(getArguments().getString("imagen")).into(foto);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_editar:

                // Crear el bundle y mandar los datos del reporte
                Bundle bundle1 = new Bundle();

              bundle1.putString("contenido1", getArguments().getString("contenido"));
                bundle1.putString("imagen1", getArguments().getString("imagen"));
                bundle1.putString("latitud1", getArguments().getString("latitud"));
                bundle1.putString("longitud1", getArguments().getString("longitud"));
                bundle1.putString("idreporte1", getArguments().getString("idreporte"));
                bundle1.putString("idusuario1", getArguments().getString("idusuario"));
                bundle1.putString("fecha1", getArguments().getString("fecha"));
                bundle1.putString("iduniversidad1", getArguments().getString("iduniversidad"));
                bundle1.putString("key", auth_token);


                EditReport editReport = new EditReport();
                editReport.setArguments(bundle1);
                //editReport.setArguments(bundle);


                FragmentTransaction transaction;
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.reports_container, editReport);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            default:

        }
    }
}
