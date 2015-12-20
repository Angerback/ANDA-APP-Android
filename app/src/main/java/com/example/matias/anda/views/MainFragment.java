package com.example.matias.anda.views;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.matias.anda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements  View.OnClickListener  {

    Integer flag = 0;
    FragmentTransaction transaction;
    private  BroadcastReceiver br = null;
    Button iniciar_sesion;
    Button registrarse;



    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstacnceState){
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        iniciar_sesion = (Button)view.findViewById(R.id.btn_iniciarsesion);
        registrarse = (Button)view.findViewById(R.id.btn_registrarse);
        iniciar_sesion.setOnClickListener(this);
        registrarse.setOnClickListener(this);
        return view;
    }

    /**
     * Método que se ejecuta luego que el fragmento se detiene
     */
    @Override
    public void onPause() {
        if (br != null) {
            getActivity().unregisterReceiver(br);
        }
        super.onPause();
    }// onPause()


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_iniciarsesion:
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Login());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.btn_registrarse:
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Register());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }
}
