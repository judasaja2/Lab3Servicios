package co.edu.udea.compumovil.gr05_20181.lab2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import co.edu.udea.compumovil.gr05_20181.lab2.data.dbHelper;
import co.edu.udea.compumovil.gr05_20181.lab2.data.usuarioContract;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Menu menu;
    private Button botonBebidas;
    private Button botonPlatos;
    private String current_language;
    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

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

        sharedPreferences = getSharedPreferences("co.edu.udea.compumovil.gr05_20181.lab2", MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean("firstRun", true)) {
            String nombre, apellido, foto, contraseña;
            dbHelper db=new dbHelper(getApplicationContext());
            ConfiguracionesActivity configuracionesActivity = new ConfiguracionesActivity();
            Cursor c = db.obtenerUsuarioPorCorreo(getIntent().getStringExtra("Usuario"));
            if(c.moveToNext()) {
                nombre = c.getString(c.getColumnIndex(usuarioContract.usuarioEntry.NOMBRE));
                apellido = c.getString(c.getColumnIndex(usuarioContract.usuarioEntry.APELLIDO));
                foto = c.getString(c.getColumnIndex(usuarioContract.usuarioEntry.FOTO));
                contraseña = c.getString(c.getColumnIndex(usuarioContract.usuarioEntry.CONTRASEÑA));
                configuracionesActivity.firstTimeRun(nombre, apellido, foto, contraseña, getApplicationContext());
                sharedPreferences.edit().putBoolean("firstRun", false).commit();
            }
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
        Intent intent = null;
        int id = item.getItemId();
        String Usuario= getIntent().getStringExtra("Usuario");

        if (id == R.id.nav_bebidas) {
            intent = new Intent(getApplicationContext(), BebidasActivity.class);
        } else if (id == R.id.nav_platos) {
            intent = new Intent(getApplicationContext(), PlatosActivity.class);
        } else if (id == R.id.nav_perfil) {
            intent = new Intent(getApplicationContext(), PerfilActivity.class);
            intent.putExtra("Usuario", Usuario);
        } else if (id == R.id.nav_acerca) {
            //intent = new Intent(getApplicationContext(), AcercaActivity.class);
        } else if (id == R.id.nav_configuracion) {
            intent = new Intent(getApplicationContext(), ConfiguracionesActivity.class);
            intent.putExtra("Usuario", Usuario);
        } else if (id == R.id.nav_cerrar_sesion) {
            //intent = new Intent(getApplicationContext(), CerrarSesionActivity.class);
        }
        if(intent != null) {
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
