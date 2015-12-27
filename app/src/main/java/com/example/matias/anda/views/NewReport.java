package com.example.matias.anda.views;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.matias.anda.R;
import com.example.matias.anda.controllers.HttpPost;
import com.example.matias.anda.controllers.UploadCouldinary;
import com.example.matias.anda.utilities.JsonHandler;
import com.example.matias.anda.utilities.SystemUtilities;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.ProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 */

public class NewReport extends Fragment implements View.OnClickListener {

    private ProgressDialog pDialog;
    private Context context;
    static final int CAM_REQUEST = 1;
    String id;
    String URL_POST = "";
    Button btn_ok;
    Button btn_capture;
    String auth_token;
    EditText et_contenido;
    EditText et_foto;
    String jsonobject;
    ImageView foto;
    Integer flag = 1;
    String URL = "http://pliskin12.ddns.net:8080/taller-bd-11/usuarios/";
    String resultado = "null";
    String latitud;
    String longitud;
    Location location;
    private LocationManager locationManager;
    boolean gpsActivo;
    private LocationListener locationListener;
    static final int MIN_TIME = 0;
    static final int MIN_DISTANCE = 0;


    /** Constructor */

    public NewReport(Context context) {

        this.context = context;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_new_report, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();

        if (flag == 1) {
            // Obtener el key enviado desde la clase Reports
            String key = getArguments().getString("key");
            JsonHandler jh = new JsonHandler();
            id = jh.getValor(key, "idUsuario");
            auth_token = jh.getValor(key, "auth_token");

            URL_POST = URL_POST.concat(URL).concat(id).concat("/").concat("reportes");
            System.out.println("La Url es: " + URL_POST);
            System.out.println("auth_token es: " + auth_token);
            System.out.println("id del usuarios es:" + id);


            et_contenido = (EditText) getView().findViewById(R.id.contenido_newreport);
            btn_capture = (Button) getView().findViewById(R.id.btn_capture);
            btn_ok = (Button) getView().findViewById(R.id.btn_ok_newreport);
            foto = (ImageView) getView().findViewById(R.id.iv_foto);
            btn_capture.setOnClickListener(this);
            btn_ok.setOnClickListener(this);
            flag = 0;
        }
    }//End onResume

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok_newreport:
                JsonHandler jsonHandler = new JsonHandler();
                jsonobject = jsonHandler.getNewReport(et_contenido.getText().toString(),
                        resultado.toString(), id, latitud, longitud);
                SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());

                if (validate()) {
                    pDialog = new ProgressDialog(getActivity());
                    pDialog.setMessage("Cargando nuevo reporte...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    if (su.isNetworkAvailable()) {
                        new HttpPost(getActivity().getApplicationContext(),
                                new HttpPost.AsyncResponse() {

                                    @Override
                                    public void processFinish(String output) {
                                        System.out.println("salida new Report:" + output + "\n");
                                        getActivity().getFragmentManager().popBackStack();
                                        pDialog.dismiss();
                                    }
                                }).execute(URL_POST, jsonobject, auth_token);
                    }//network-available
                    else {
                        Toast toast = Toast.makeText(this.context, "NO HAY CONEXION A INTERNET",
                                Toast.LENGTH_LONG);
                        toast.show();
                    }

                } //if-validate
                else {
                    Toast toast = Toast.makeText(this.context, "Ingrese los datos",
                            Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            case R.id.btn_capture:


                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, CAM_REQUEST);
                getLocation();
                break;



        }
    }// onClick


    private boolean validate() {

        if (et_contenido.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }// End Validate


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAM_REQUEST) {
                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                foto.setImageBitmap(cameraImage);
                Uri temUri = getImageUri(getActivity().getApplicationContext(), cameraImage);
                File finaleFile = new File(getRealPathFromURI(temUri, getActivity().getApplicationContext()));
                System.out.println("RUTA ABSOLUTA" + finaleFile);

                new UploadCouldinary(getActivity().getApplicationContext(),
                        new UploadCouldinary.AsyncResponse() {
                            @Override
                            public void processFinish(String output) {

                                resultado = output;
                                System.out.println(resultado);
                            }
                        }).execute(finaleFile.toString());

            }

        }
    }// End onActivityResult


    /** Obtener el URI desde el BipMap */
    public Uri getImageUri(Context context, Bitmap image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), image, "Title", null);
        return Uri.parse(path);
    }//Enf getImageUri

    /** Obtener la ruta absoluta */
    public String getRealPathFromURI(Uri uri, Context context) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }// Enf getRealPathFromURI


    public void getLocation() {

        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        gpsActivo = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gpsActivo) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    System.out.println("NO HAY PERMISOS");

                } else {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            } else {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }


        } else {
            Toast.makeText(this.context, "EL GPS NO ESTA ACTIVADO", Toast.LENGTH_LONG).show();
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mostrarLocalizacion(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);




    }// End getLocation()


    public void mostrarLocalizacion(Location loc){
        if(location != null){
            latitud = String.valueOf(loc.getLatitude());
            longitud = String.valueOf(loc.getLongitude());
            System.out.println(latitud);
            System.out.println(longitud);
        }
        else{
            System.out.println("ESTA NULO");
        }
    }




}

