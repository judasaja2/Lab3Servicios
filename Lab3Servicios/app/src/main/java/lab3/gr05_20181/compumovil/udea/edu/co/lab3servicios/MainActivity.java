package lab3.gr05_20181.compumovil.udea.edu.co.lab3servicios;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import lab3.gr05_20181.compumovil.udea.edu.co.lab3servicios.data.ResponseBebida;
import lab3.gr05_20181.compumovil.udea.edu.co.lab3servicios.data.ResponsePlato;
import lab3.gr05_20181.compumovil.udea.edu.co.lab3servicios.data.ResponseUsuario;
import lab3.gr05_20181.compumovil.udea.edu.co.lab3servicios.data.remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mResponseTv;
    private Retrofit retrofit;
    private static APIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final String url = "http://192.168.1.73:3000/";

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(APIService.class);

        final EditText titleEt = (EditText) findViewById(R.id.et_title);
        final EditText bodyEt = (EditText) findViewById(R.id.et_body);
        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        mResponseTv = (TextView) findViewById(R.id.tv_response);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        new PeticionPlatoGet().execute();
        new PeticionPlatoPost().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static class PeticionUsuarioGet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Call<List<ResponseUsuario>> response = mApiService.getUsers();
                for (ResponseUsuario usuario : response.execute().body()) {
                    Log.e("Usuarios: ", usuario.getNombre());
                }
            } catch(IOException e){
                Log.e("ERROR", e.toString());
            }
            return null;
        }
    }

    public static class PeticionUsuarioPost extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Call<ResponseUsuario> response = mApiService.postUser("PRUEBAS111".trim(), "PRUEBAS".trim(), "PRUEBAS".trim(), "PRUEBAS".trim(), "PRUEBAS".trim());
                response.execute();
            } catch(Exception e){
                Log.e("ERROR", e.toString());
            }
            return null;
        }
    }

    public static class PeticionBebidaGet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Call<List<ResponseBebida>> response = mApiService.getBebidas();
                for (ResponseBebida bebida : response.execute().body()) {
                    Log.e("Bebidas: ", bebida.getNombre());
                }
            } catch(IOException e){
                Log.e("ERROR", e.toString());
            }
            return null;
        }
    }

    public static class PeticionBebidaPost extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Call<ResponseBebida> response = mApiService.postBebida("PRUEBA".trim(), "PRUEBA".trim(), 1500, "PRUEBA".trim());
                response.execute();
            } catch(Exception e){
                Log.e("ERROR", e.toString());
            }
            return null;
        }
    }

    public static class PeticionPlatoGet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Call<List<ResponsePlato>> response = mApiService.getPlatos();
                for (ResponsePlato plato : response.execute().body()) {
                    Log.e("Platos: ", plato.getNombre());
                }
            } catch(IOException e){
                Log.e("ERROR", e.toString());
            }
            return null;
        }
    }

    public static class PeticionPlatoPost extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Call<ResponsePlato> response = mApiService.postPlato("PRUEBA".trim(), "PRUEBA".trim(), "PRUEBA".trim(), "PRUEBA".trim(), "PRUEBA".trim(), "PRUEBA".trim(), 1500);
                response.execute();
            } catch(Exception e){
                Log.e("ERROR", e.toString());
            }
            return null;
        }
    }

}
