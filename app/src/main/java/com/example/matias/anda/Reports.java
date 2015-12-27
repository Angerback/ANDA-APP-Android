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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.reports_container, new ViewReports());
        transaction.addToBackStack("irViewReports");
        transaction.commit();


    }

    @Override
    protected void onStart() {
        super.onStart();
        // Obtener los datos enviados desde el login.
        Intent intent = getIntent();
        token = intent.getStringExtra("auth_key");

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
                transaction.replace(R.id.reports_container, new MyReports());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.menu_reports_newreport:
                Bundle bundle = new Bundle();
                bundle.putString("key",token);

                //Llamar al fragmento
                transaction = getFragmentManager().beginTransaction();
                NewReport newReport = new NewReport(getApplicationContext());
                newReport.setArguments(bundle);
                transaction.replace(R.id.reports_container,newReport);
                transaction.addToBackStack(null);
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
            getFragmentManager().popBackStack("irViewReports",0);
        }
    }// onBackPressed()
}
