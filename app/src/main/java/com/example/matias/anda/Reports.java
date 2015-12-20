package com.example.matias.anda;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.example.matias.anda.views.MyReports;
import com.example.matias.anda.views.NewReport;

public class Reports extends AppCompatActivity {

    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
    }

    /**
     * Método que crea el menú superior
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reports, menu);
        return true;
    }// onCreateOptionsMenu(Menu menu)

    /**
     * Método que escucha los elementos presionados en el menú superior
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_reports_salir:
                finish();
                System.exit(0);
                break;
            case R.id.menu_reports_myreports:
                // Llamar al fragmento
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.reports_container, new MyReports());
                transaction.commit();
                break;
            case R.id.menu_reports_newreport:
                //Llamar al fragmento
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.reports_container, new NewReport());
                transaction.commit();
                break;
            case R.id.menu_main_cerrar_sesion:
                //Llamar al fragmento

                break;
        }
        return super.onOptionsItemSelected(item);
    }// onOptionsItemSelected(MenuItem item)

    /**
     * Método que se ejecuta al momento de presionar el botón regresar del teclado
     */
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }// onBackPressed()
}
