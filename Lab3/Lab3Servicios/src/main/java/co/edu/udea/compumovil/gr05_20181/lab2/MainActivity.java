package co.edu.udea.compumovil.gr05_20181.lab2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import co.edu.udea.compumovil.gr05_20181.lab2.data.remote.APIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Menu menu;
    private Button botonBebidas;
    private Button botonPlatos;
    private String current_language;
    SharedPreferences sharedPreferences = null;
    public static Context contextMain;
    private String nombreUsuario, apellidoUsuario, correoUsuario, fotoUsuario;
    private TextView navHeaderMainNombre, navHeaderMainCorreo;
    private ImageView mainContentBebidaImage, mainContentPlatoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferences = getSharedPreferences("co.edu.udea.compumovil.gr05_20181.lab2", MODE_PRIVATE);

        contextMain = getApplicationContext();

        mainContentBebidaImage = findViewById(R.id.mainContentBebidaImage);
        mainContentPlatoImage = findViewById(R.id.mainContentPlatoImage);

        mainContentBebidaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BebidasActivity.class);
                startActivity(intent);
            }
        });
        mainContentPlatoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlatosActivity.class);
                startActivity(intent);
            }
        });



        //getExtras();
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
        /*if (sharedPreferences.getBoolean("firstRun", true)) {
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
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        navHeaderMainNombre = findViewById(R.id.navHeaderMainNombre);
        navHeaderMainCorreo = findViewById(R.id.navHeaderMainCorreo);

        navHeaderMainNombre.setText(nombreUsuario);
        navHeaderMainCorreo.setText(correoUsuario);
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
        Intent intent = null;
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            intent = new Intent(getApplicationContext(), PerfilActivity.class);
            intent.putExtra("nombreUsuario", this.nombreUsuario);
            intent.putExtra("apellidoUsuario", this.apellidoUsuario);
            intent.putExtra("correoUsuario", this.correoUsuario);
            intent.putExtra("fotoUsuario", this.fotoUsuario);
        } else if (id == R.id.nav_acerca) {
            //intent = new Intent(getApplicationContext(), AcercaActivity.class);
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

    public void getExtras(){
        Bundle bundle = getIntent().getExtras();
        this.nombreUsuario = bundle.getString("nombreUsuario");
        this.apellidoUsuario = bundle.getString("apellidoUsuario");
        this.correoUsuario = bundle.getString("correoUsuario");
        this.fotoUsuario = bundle.getString("fotoUsuario");
    }


}
