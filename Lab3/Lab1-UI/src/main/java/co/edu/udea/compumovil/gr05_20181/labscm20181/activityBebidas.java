package co.edu.udea.compumovil.gr05_20181.labscm20181;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.Locale;

import gun0912.tedbottompicker.TedBottomPicker;

public class activityBebidas extends AppCompatActivity {

    private Menu menu;

    private static final String RESUME_KEY = "resume";
    private static final String FOTO_KEY = "foto";
    private String datosrRecuperados, datosrRecuperados2;
    private Uri selectedUri = null;
    private ImageView iv_image;
    private EditText campoNombre, campoPrecio, campoIngredientes;
    private Button botonGaleria, botonRegistrar;
    private TextView cuadroDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String lang = intent.getStringExtra("lang");
        setLanguage(lang);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);
        campoNombre = (EditText) findViewById(R.id.editTextNombreBebida);
        campoPrecio = (EditText) findViewById(R.id.editTextPrecioBebida);
        campoIngredientes = (EditText) findViewById(R.id.editTextIngredientesBebida);
        botonGaleria = (Button) findViewById(R.id.botonGaleriaBebida);
        botonRegistrar = (Button) findViewById(R.id.botonRegistrarBebidas);
        cuadroDatos = (TextView) findViewById(R.id.cuadroDatos);
        iv_image = (ImageView) findViewById(R.id.imageViewBebida);
        if (savedInstanceState != null) {
            datosrRecuperados = savedInstanceState.getString(RESUME_KEY);
            datosrRecuperados2 = savedInstanceState.getString(FOTO_KEY);

            cuadroDatos.setText(datosrRecuperados);
            if (datosrRecuperados2 != null)
                selectedUri = Uri.parse(datosrRecuperados2);
            if (selectedUri != null) {
                Glide.with(activityBebidas.this)
                        .load(selectedUri)
                        .into(iv_image);
            }
        }
        cuadroDatos.setMovementMethod(new ScrollingMovementMethod());
        botonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingleShowButton();
            }
        });
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuadroDatos.setText(cuadroDatos.getText() + campoNombre.getHint().toString() + ": " + campoNombre.getText() + "\n");
                cuadroDatos.setText(cuadroDatos.getText() + campoPrecio.getHint().toString() + ": " + campoPrecio.getText() + "\n");
                cuadroDatos.setText(cuadroDatos.getText() + campoIngredientes.getHint().toString() + ": " + campoIngredientes.getText() + "\n\n");
                datosrRecuperados = String.valueOf(cuadroDatos.getText());
                guardarPreferencias(cuadroDatos.getText().toString());
            }
        });
        cargarPreferencias(cuadroDatos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem opcionMenu) {
        int id = opcionMenu.getItemId();
        if (id == R.id.limpiar) {
            cuadroDatos.setText("");
        } else if (id == R.id.salir) {
            System.exit(1);
        } else if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        datosrRecuperados = String.valueOf(cuadroDatos.getText());
        savedInstanceState.putString(RESUME_KEY, datosrRecuperados);
        savedInstanceState.putString(FOTO_KEY, datosrRecuperados2);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void setSingleShowButton() {
        TedPermission.with(activityBebidas.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Debe aceptar los permisos")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(activityBebidas.this)
                    .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                        @Override
                        public void onImageSelected(Uri uri) {
                            selectedUri = uri;
                            datosrRecuperados2 = String.valueOf(selectedUri);
                            Glide.with(activityBebidas.this)
                                    .load(uri)
                                    .into(iv_image);
                        }
                    })
                    .create();
            tedBottomPicker.show(getSupportFragmentManager());
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(activityBebidas.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private void guardarPreferencias(String campoDato){
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Bebidas", campoDato);
        editor.commit();
    }

    private void cargarPreferencias(TextView campoDato){
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dato = preferences.getString("Bebidas", "");
        campoDato.setText(dato);
    }
}
