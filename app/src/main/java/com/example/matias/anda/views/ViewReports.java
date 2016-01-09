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

            final String[] jsonReportList =  new String[jsonArray.length()];

            // Hacer el String[] para luego mostrarlo en un listView
            for(int i = 0; i < jsonArray.length(); i++) {
                String report = jsonArray.getJSONObject(i).getString("contenido");
                jsonReportList[i] = report;
            }

            ListView listReports = (ListView) getActivity().findViewById(R.id.listViewReports);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, jsonReportList);

            listReports.setAdapter(arrayAdapter);

            /** Se escucha el click de la lista y se muestra el detalle del reporte*/
            listReports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String item = (String)parent.getItemAtPosition(position);

                    /** mensaje que muestra solo el contenido del reporte*/
                    Toast.makeText(getActivity().getBaseContext(), item, Toast.LENGTH_LONG).show();

                    /** se muestra el fragmento con el detalle del reporte*/
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    ReportDetail reportsDetail = new ReportDetail();
                    Bundle bundle = new Bundle();
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
