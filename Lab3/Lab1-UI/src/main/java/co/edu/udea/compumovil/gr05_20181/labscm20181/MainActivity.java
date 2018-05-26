package co.edu.udea.compumovil.gr05_20181.labscm20181;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Menu menu;
    private Button botonBebidas;
    private Button botonPlatos;
    private String current_language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonBebidas = (Button) findViewById(R.id.botonBebidas);
        botonPlatos = (Button) findViewById(R.id.botonPlatos);
        if(botonBebidas.getText().toString().equals("Bebidas")){
            current_language = "es";
        } else {
            current_language = "en";
        }
        botonBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividadBebidas = new Intent(getApplicationContext(), activityBebidas.class);
                if(current_language.equals("es")) {
                    actividadBebidas.putExtra("lang", "es");
                } else {
                    actividadBebidas.putExtra("lang", "en");
                }
                startActivity(actividadBebidas);
            }
        });
        botonPlatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividadPlatos = new Intent(getApplicationContext(), activityPlatos.class);
                if(current_language.equals("es")) {
                    actividadPlatos.putExtra("Lang", 0);
                } else {
                    actividadPlatos.putExtra("Lang", 1);
                }
                startActivity(actividadPlatos);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem opcionMenu) {
        int id = opcionMenu.getItemId();
        if (id == R.id.salir) {
            System.exit(1);
        } else if (id == R.id.cambiarIdioma) {
            Log.d("Language", current_language);
            if(current_language.equals("en")){
                setLanguage("es");
            } else {
                setLanguage("en");
            }
        }
        return true;
    }

    public void setLanguage(String lang){
        current_language = lang;
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        recreate();
    }

}
