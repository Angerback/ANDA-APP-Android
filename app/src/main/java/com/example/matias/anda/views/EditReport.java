package com.example.matias.anda.views;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.matias.anda.R;
import com.example.matias.anda.controllers.HttpPut;
import com.example.matias.anda.controllers.UploadCouldinary;
import com.example.matias.anda.utilities.JsonHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditReport extends Fragment implements View.OnClickListener{


    String id;
    String auth_token;
    EditText edit_contenido;
    Button edit_ok;
    String URL_PUT =   "http://pliskin12.ddns.net:8080/taller-bd-11/reportes/edit/";

    FloatingActionButton btn_capture;
    ImageView foto;
    public static int count=0;
    public String dir;
    Uri outputFileUri;
    static final int CAM_REQUEST = 1;
    String pathToImage;
    String resultado = "null";


    public EditReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_edit_report, container, false);
        auth_token = getArguments().getString("key");
        String id_reporte = (String) getArguments().get("idreporte1");
        URL_PUT = URL_PUT + id_reporte;


/*        String str1 = getArguments().getString("contenido1");
        System.out.println("edit " + str1);
        System.out.println("edit " + getArguments().getString("imagen1"));
        System.out.println("edit " + getArguments().getString("latitud1"));
        System.out.println("edit " + getArguments().getString("longitud1"));
        System.out.println("edit " + getArguments().getString("iduniversidad1"));
        System.out.println("edit " + getArguments().getString("fecha1"));
        System.out.println("edit " + getArguments().getString("idreporte1"));
        System.out.println("edit " + getArguments().getString("idusuario1"));*/


        edit_ok = (Button)v.findViewById(R.id.btn_edit_ok);
        edit_ok.setOnClickListener(this);
        return v;
    }


    @Override
    public void onResume() {
super.onResume();

        edit_contenido = (EditText) getView().findViewById(R.id.et_edit_contenido);

        //para tomar la imagen nueva
        btn_capture = (FloatingActionButton) getView().findViewById(R.id.btn_imagen_edit);
        foto = (ImageView) getView().findViewById(R.id.iv_foto);
        btn_capture.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_edit_ok:
                System.out.println("ENTROO LA MIERDA ?? ");

                /**new UploadCouldinary(getActivity().getApplicationContext(),
                        new UploadCouldinary.AsyncResponse() {
                            @Override
                            public void processFinish(String output) {
                                resultado = output;
                                System.out.println(resultado);
                            }
                        }, this.myHandler).execute(pathToImage
                );*/

                String json;
                JsonHandler jsonHandler = new JsonHandler();
                json = jsonHandler.editReport(edit_contenido.getText().toString(),getArguments().getString("imagen1"),
                        getArguments().getString("idusuario1"),getArguments().getString("latitud1"),
                        getArguments().getString("longitud1"),getArguments().getString("iduniversidad1"),
                        getArguments().getString("fecha1"));


                new HttpPut(getActivity().getApplicationContext(), new HttpPut.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        System.out.println(output);
                    }
                }).execute(URL_PUT,json,auth_token);

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


    }

    /** Método que gestiona la camara del telefono*/
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAM_REQUEST) {
                // Se obtiene la ruta
                pathToImage = outputFileUri.getPath();

                // muestra foto en imageview con el path de la imagen
                Glide.with(this).load(pathToImage).centerCrop().into(foto);
                // Se muestra en pantalla como un bitmap
                //foto.setImageBitmap(BitmapFactory.decodeFile(pathToImage));
            }
        }
    }// End onActivityResult
}
