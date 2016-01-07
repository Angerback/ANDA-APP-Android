package com.example.matias.anda.views;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.matias.anda.R;
import com.example.matias.anda.controllers.HttpGet;
import com.example.matias.anda.controllers.HttpPost;
import com.example.matias.anda.controllers.UploadCouldinary;
import com.example.matias.anda.utilities.GPSTracker;
import com.example.matias.anda.utilities.JsonHandler;
import com.example.matias.anda.utilities.SystemUtilities;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.app.ProgressDialog;

import android.support.design.widget.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NewReport extends Fragment implements View.OnClickListener, OnMapReadyCallback{

    String URL = "http://pliskin12.ddns.net:8080/taller-bd-11/usuarios/";
    String URL_UES = "http://pliskin12.ddns.net:8080/taller-bd-11/universidades";
    private ProgressDialog pDialog;
    private Context context;
    static final int CAM_REQUEST = 1;
    String resultado = "null";
    String URL_POST = "";
    EditText et_contenido;
    FloatingActionButton btn_ok;
    FloatingActionButton btn_capture;
    String auth_token;
    String jsonobject;
    ImageView foto;
    Integer flag = 1;
    String latitud;
    String longitud;
    String id;
    GPSTracker tracker;
    double latDob = 0.0;
    double lonDob = 0.0;
    public static int count=0;
    public String dir;
    Uri outputFileUri;
    String pathToImage;
    Spinner spinner;
    Map<String, String> universidades = new HashMap<>();

    /** Constructor */
    public NewReport(){

    }
    public NewReport(Context context) {
        this.context = context;
        tracker = new GPSTracker(this.context, myHandler);

    }
    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (!tracker.canGetLocation()) {
            tracker.showSettingsAlert();
        } else {
            latDob = tracker.getLatitude();
            lonDob = tracker.getLongitude();
            latitud = Double.toString(latDob);
            longitud = Double.toString(lonDob);
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.setMyLocationEnabled(true);
            googleMap.setTrafficEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latDob, lonDob)).zoom(17).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            setUpMap();

        }

    }

    private void setUpMap(){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latDob, lonDob)).zoom(17).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    /** Método que crea la vista del fragmento */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_new_report_v2, container, false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
        //Ahora el metodo onMapReady es ejecutado cuando el mapa esta listo para ser mostrado


        //Se crea una carpeta "Anda" para guardar las fotos tomadas por la camara
        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Anda/";
        File newdir = new File(dir);
        newdir.mkdirs();

        return v;
    }// End onCreateView

    /** Método que se ejecuta luego de que el fragmento es creado */
    @Override
    public void onResume() {
        super.onResume();


        if (flag == 1) {
            mMapView.onResume();
            // Obtener el key enviado desde la clase Reports
            String key = getArguments().getString("key");
            JsonHandler jh = new JsonHandler();
            id = jh.getValor(key, "idUsuario");
            auth_token = jh.getValor(key, "auth_token");
            URL_POST = URL_POST.concat(URL).concat(id).concat("/").concat("reportes");
            System.out.println("La Url es: " + URL_POST);

            // Get para el combobox con UES
            getUniversidades();

            et_contenido = (EditText) getView().findViewById(R.id.contenido_newreport);
            btn_capture = (FloatingActionButton) getView().findViewById(R.id.btn_capture);
            btn_ok = (FloatingActionButton) getView().findViewById(R.id.btn_ok_newreport);
            foto = (ImageView) getView().findViewById(R.id.iv_foto);
            btn_capture.setOnClickListener(this);
            btn_ok.setOnClickListener(this);

            flag = 0;
        }
    }//End onResume


    /** Método que se ejecuta al presionar el boton */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok_newreport:
                if (validate()) {
                    pDialog = new ProgressDialog(getActivity());
                    pDialog.setMessage("Cargando nuevo reporte...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    new UploadCouldinary(getActivity().getApplicationContext(),
                            new UploadCouldinary.AsyncResponse() {
                                @Override
                                public void processFinish(String output) {
                                    resultado = output;
                                    System.out.println(resultado);
                                }
                            }, this.myHandler).execute(pathToImage
                    );


                } //if-validate
                else {
                    Toast toast = Toast.makeText(this.context, "Ingrese los datos",
                            Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            case R.id.btn_capture:

                // Se incremente el contador cada vez que una foto es guardada, "1.jpg","2.jpg",...
                count++;
                String file = dir+count+".jpg";
                File newfile = new File(file);
                outputFileUri = Uri.fromFile(newfile);
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(camera_intent, CAM_REQUEST);
                
                break;

        }
    }// onClick

    /** Validacion de los campos ingresados */
    private boolean validate() {
        if (et_contenido.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }// End Validate

    /** Método que gestiona la camara del telefono*/
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAM_REQUEST) {
                // Se obtiene la ruta
                pathToImage = outputFileUri.getPath();
                // Se muestra en pantalla como un bitmap
                foto.setImageBitmap(BitmapFactory.decodeFile(pathToImage));
            }
        }
    }// End onActivityResult



    Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String idUniversidad = universidades.get(spinner.getSelectedItem());
                    System.out.println("Universidad seleccionada: " + spinner.getSelectedItem() + ", ID: " + idUniversidad +"\n");
                    //Llega la foto
                    JsonHandler jsonHandler = new JsonHandler();
                    jsonobject = jsonHandler.getNewReport(et_contenido.getText().toString(),
                            resultado.toString(), id, latitud, longitud, idUniversidad);
                    SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
                    if (new SystemUtilities(getActivity().getApplicationContext()).isNetworkAvailable()) {
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
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "NO HAY CONEXION A INTERNET",
                                Toast.LENGTH_LONG);
                        toast.show();
                        pDialog.dismiss();
                    }
                    break;
                case 1:
                    latDob = tracker.getLatitude();
                    lonDob = tracker.getLongitude();
                    setUpMap();
                    break;
                case 2:

                default:
                    break;

            }
        }
    };

    private void getUniversidades() {

            new HttpGet(getActivity().getApplicationContext(),
                    new HttpGet.TaskResult() {
                        @Override
                        public void onSuccess(String result) {
                            addtoCombobox(result);

                        }
                    }).execute(URL_UES, auth_token);
            //
    }

    private void addtoCombobox(String listaUes) {

        int dftIndex = 0;

        try {
            JSONArray ja =  new JSONArray(listaUes);
            String[] result = new String[ja.length()];
            String uni;
            for(int i=0;i< ja.length();i++){
                JSONObject row = ja.getJSONObject(i);
                uni = (String) row.get("nombre")/* + " " + row.getString("id")*/;
                this.universidades.put((String )row.get("nombre"),  row.getString("id"));
                result[i] = uni;

                if(ja.getJSONObject(i).getString("nombre").equalsIgnoreCase("Kristinehamn")){
                    dftIndex = i;
                }
            }

            this.spinner = (Spinner) getView().findViewById(R.id.spinner_ues);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context,android.R.layout.simple_spinner_item,
                    result);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}// END NewReport

