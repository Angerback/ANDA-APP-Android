package com.example.matias.anda;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.matias.anda.views.MainFragment;


public class MainActivity extends AppCompatActivity {


    public static String PREFS_NAME = "mypre";
    public static String PREF_USERNAME = "username";
    public static String PREF_PASSWORD = "password";
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, new MainFragment());
        transaction.commit();
    }

    @Override
    public void onStart(){
        super.onStart();
        getUser();
    }

    private void getUser() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD,null);

        if(username != null || password != null){

        }
    }


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

    /**
     * Método que crea el menú superior
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }// onCreateOptionsMenu(Menu menu)

    /**
     * Método que escucha los elementos presionados en el menú superior
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_main_activity_exit:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }// onOptionsItemSelected(MenuItem item)
}
