package com.example.matias.anda.views;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matias.anda.R;
import com.example.matias.anda.controllers.HttpGet;
import com.example.matias.anda.utilities.JsonHandler;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyReports extends Fragment {

    Context context;
    String URL_GET =
            "http://pliskin12.ddns.net:8080/taller-bd-11/usuarios/";
    String id;
    String auth_token;

    public MyReports() {
        // Required empty public constructor
    }

    public MyReports(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_reports, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        // Obtener el key(id + autenticacion) desde el Bundle
        String key = getArguments().getString("key");
        // Obtener el id del usuario
        JsonHandler jh = new JsonHandler();
        id = jh.getValor(key, "idUsuario");
        // Obtener la autenticación
        auth_token = jh.getValor(key, "auth_token");

        URL_GET = URL_GET+id+"/reportes";

        new HttpGet(getActivity().getApplicationContext(), new HttpGet.TaskResult() {
            @Override
            public void onSuccess(String result) {
                System.out.println("MR == " +result );
                manejarMisReportes(result);

            }
        }).execute(URL_GET, auth_token);
    }

    private void manejarMisReportes(String result) {
        try {
            JSONArray jsonArray1 = new JSONArray(result);
            JSONArray jsonArray = new JSONArray();
            for(int i = jsonArray1.length()-1; i >= 0 ; i--){
                jsonArray.put(jsonArray1.get(i));
            }

            final String[] contenido_report =  new String[jsonArray.length()];
            final String[] imagen_report =  new String[jsonArray.length()];
            final String[] latitud_report =  new String[jsonArray.length()];
            final String[] longitud_report =  new String[jsonArray.length()];
            final String[] universidad_report =  new String[jsonArray.length()];

            // Hacer el String[] para luego mostrarlo en un listView

            for(int i = 0; i < jsonArray.length(); i++) {

                String report = jsonArray.getJSONObject(i).getString("contenido");
                contenido_report[i] = report;

                String imagen_detail = jsonArray.getJSONObject(i).getString("foto");
                imagen_report[i] = imagen_detail;

                String latitud_detail = jsonArray.getJSONObject(i).getString("latitud");
                latitud_report[i] = latitud_detail;

                String longitud_detail = jsonArray.getJSONObject(i).getString("longitud");
                longitud_report[i] = longitud_detail;

                String universidad_detail = jsonArray.getJSONObject(i).getString("longitud");
                universidad_report[i] = universidad_detail;

            }

            ListView listReports = (ListView) getActivity().findViewById(R.id.listViewMyReports);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, contenido_report);

            listReports.setAdapter(arrayAdapter);

            /** Se escucha el click de la lista y se muestra el detalle del reporte*/
            listReports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String item = (String)parent.getItemAtPosition(position);
                    Bundle bundle = new Bundle();

                    /** mensaje que muestra solo el contenido del reporte*/
                    Toast.makeText(getActivity().getBaseContext(), item, Toast.LENGTH_LONG).show();

                    /** se captan los valores de los elementos del reporte seleccionado*/
                    bundle.putString("contenido", contenido_report[position]);
                    bundle.putString("imagen", imagen_report[position]);
                    bundle.putString("latitud", latitud_report[position]);
                    bundle.putString("longitud", longitud_report[position]);
                    bundle.putString("idUniversidad", universidad_report[position]);


                    /** se muestra el fragmento con el detalle del reporte*/
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    MyReportDetail myReportsDetail = new MyReportDetail();

                    bundle.putString("key", auth_token);
                    myReportsDetail.setArguments(bundle);
                    transaction.replace(R.id.reports_container, myReportsDetail);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
