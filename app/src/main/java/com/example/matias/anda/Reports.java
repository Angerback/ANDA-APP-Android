package com.example.matias.anda;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.example.matias.anda.views.MyReports;
import com.example.matias.anda.views.NewReport;
import com.example.matias.anda.views.ViewReports;

public class Reports extends AppCompatActivity {


    String token;
    FragmentTransaction transaction;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        // Obtener los datos enviados desde el login (id,key)
        Intent intent = getIntent();
        token = intent.getStringExtra("auth_key");


        //Configurar el bundle
        bundle = new Bundle();
        bundle.putString("key", token);

        //LLamar al fragmento
        callFragmentViewReport();

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
                Intent salir = new Intent(this, MainActivity.class);
                salir.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                salir.putExtra("Exit me", true);
                startActivity(salir);
                finish();
                break;

            case R.id.menu_reports_myreports:
                // Llamar al fragmento
                transaction = getFragmentManager().beginTransaction();
                MyReports myReports = new MyReports(getApplicationContext());
                myReports.setArguments(bundle);
                transaction.replace(R.id.reports_container, myReports);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.menu_reports_newreport:

                //Llamar al fragmento
                transaction = getFragmentManager().beginTransaction();
                NewReport newReport = new NewReport(getApplicationContext());
                newReport.setArguments(bundle);
                transaction.replace(R.id.reports_container,newReport);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.menu_reports_logout:
                //Llamar al fragmento
                Intent cerrar_sesion = new Intent(this, MainActivity.class);
                cerrar_sesion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                cerrar_sesion.putExtra("Exit me", false);
                startActivity(cerrar_sesion);
                finish();
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
            getFragmentManager().popBackStack("irViewReports",0);
        }
    }// onBackPressed()

    /** Método que llama al fragmento viewReportes */

    private void callFragmentViewReport() {

        transaction = getFragmentManager().beginTransaction();
        ViewReports viewReports = new ViewReports();
        viewReports.setArguments(bundle);
        transaction.add(R.id.reports_container, viewReports);
        transaction.addToBackStack("irViewReports");
        transaction.commit();

    }
}
