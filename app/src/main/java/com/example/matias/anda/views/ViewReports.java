package com.example.matias.anda.views;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.matias.anda.R;
import com.example.matias.anda.controllers.HttpGet;
import com.example.matias.anda.utilities.JsonHandler;
import com.example.matias.anda.utilities.SystemUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Fragmento que se utiliza para mostrar la lista de
 * los ultimos reportes
 */
public class ViewReports extends Fragment {


    Context context;
    String id;
    String auth_token;
    static String GET_REPORTES = "get_reportes";

    static final String URL_GET = "http://pliskin12.ddns.net:8080/taller-bd-11/reportes/rango/0/9";

    private ArrayList<String[]> reportsDetail = new ArrayList<>();

    /** Constructor */
    public ViewReports(){

    }

    /** Metodo que se ejecuta cuando se crea la vista del fragmento */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_view_reports, container, false);
    }

    /** Metodo que se ejecuta una vez creada la vista */
    @Override
    public void onResume() {
        super.onResume();


        String token_id = getArguments().getString("key");
        JsonHandler jh = new JsonHandler();
        id = jh.getValor(token_id, "idUsuario");
        auth_token = jh.getValor(token_id, "auth_token");



        new HttpGet(getActivity().getApplicationContext(), new HttpGet.TaskResult() {
            @Override
            public void onSuccess(String result) {
                System.out.println("VR == "+result);
                prepararListaReportes(result);
            }
        }).execute(URL_GET,auth_token);


    }

/** Metodo para preparar la lista que se mostrara en el listview */
    private void prepararListaReportes(String jsonReportes) {


        try {
            JSONArray jsonArray = new JSONArray(jsonReportes);

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

            ListView listReports = (ListView) getActivity().findViewById(R.id.listViewReports);

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
                    ReportDetail reportsDetail = new ReportDetail();

                    bundle.putString("key", auth_token);
                    reportsDetail.setArguments(bundle);
                    transaction.replace(R.id.reports_container, reportsDetail);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


}
