package co.edu.udea.compumovil.gr05_20181.lab2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
import java.util.List;
import java.util.Locale;

import co.edu.udea.compumovil.gr05_20181.lab2.data.bebida;
import co.edu.udea.compumovil.gr05_20181.lab2.data.bebidaContract;
import co.edu.udea.compumovil.gr05_20181.lab2.data.dbHelper;
import gun0912.tedbottompicker.TedBottomPicker;

import static android.util.Log.println;

public class BebidasActivity extends AppCompatActivity {

    private Menu menu;

    private static final String RESUME_KEY = "resume";
    private static final String FOTO_KEY = "foto";
    private String datosrRecuperados, datosrRecuperados2;
    private Uri selectedUri = null;
    private ImageView iv_image;
    private EditText campoNombre, campoPrecio, campoIngredientes;
    private Button botonGaleria, botonRegistrar;
    private RecyclerView recyclerViewBebida;
    private RecyclerViewAdapterBebida adaptadorBebida;
    private bebida bebida = null;
    private List<bebida> bebidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);
        campoNombre = (EditText) findViewById(R.id.editTextNombreBebida);
        campoPrecio = (EditText) findViewById(R.id.editTextPrecioBebida);
        campoIngredientes = (EditText) findViewById(R.id.editTextIngredientesBebida);
        botonGaleria = (Button) findViewById(R.id.botonGaleriaBebida);
        botonRegistrar = (Button) findViewById(R.id.botonRegistrarBebidas);
        iv_image = (ImageView) findViewById(R.id.imageViewBebida);
        recyclerViewBebida = (RecyclerView) findViewById(R.id.recycler_bebidas);
        if (savedInstanceState != null) {
            datosrRecuperados = savedInstanceState.getString(RESUME_KEY);
            datosrRecuperados2 = savedInstanceState.getString(FOTO_KEY);
            if (datosrRecuperados2 != null)
                selectedUri = Uri.parse(datosrRecuperados2);
            if (selectedUri != null) {
                Glide.with(BebidasActivity.this)
                        .load(selectedUri)
                        .into(iv_image);
            }
        }
        botonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             setSingleShowButton();
               //obtenerTodasLasBebidas();

            }
        });
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String foto = datosrRecuperados2;
                String nombre, precio, ingredientes;
                nombre = String.valueOf(campoNombre.getText());
                precio = String.valueOf(campoPrecio.getText());
                ingredientes = String.valueOf(campoIngredientes.getText());
                bebida = new bebida(nombre, foto, Float.parseFloat(precio), ingredientes);
                dbHelper db = new dbHelper(getApplicationContext());
                db.guardarBebida(bebida);

                actualizarBebidas();
            }
        });
       actualizarBebidas();
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
            campoNombre.setText("");
            campoIngredientes.setText("");
        } else if (id == R.id.salir) {
            System.exit(1);
        } else if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
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
        TedPermission.with(BebidasActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Debe aceptar los permisos")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(BebidasActivity.this)
                    .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                        @Override
                        public void onImageSelected(Uri uri) {
                            selectedUri = uri;
                            datosrRecuperados2 = String.valueOf(selectedUri);
                            Glide.with(BebidasActivity.this)
                                    .load(uri)
                                    .into(iv_image);
                        }
                    })
                    .create();
            tedBottomPicker.show(getSupportFragmentManager());
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(BebidasActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
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

    private void obtenerTodasLasBebidas(){
        dbHelper db=new dbHelper(getApplicationContext());
        String nombre,precio,foto,ingredientes;
        Cursor c=db.obtenerTodasLasBebidas();
        Uri fotoUri;
        bebidas = new ArrayList<>();
        while(c.moveToNext()){ //se obtiene nombre, precio, foto e ingredientes por registro, por eso esta en un while
            nombre = c.getString(c.getColumnIndex(bebidaContract.bebidaEntry.NOMBRE));
            precio= c.getString(c.getColumnIndex(bebidaContract.bebidaEntry.PRECIO));
            foto = c.getString(c.getColumnIndex(bebidaContract.bebidaEntry.FOTO));
            fotoUri=Uri.parse(foto);
            ingredientes = c.getString(c.getColumnIndex(bebidaContract.bebidaEntry.INGREDIENTES));
            println(Log.INFO,"MYTAG",nombre+" "+precio+" "+foto+" "+ingredientes);
            bebida = new bebida(nombre, foto, Float.parseFloat(precio), ingredientes);
            bebidas.add(bebida);
        }
    }

    private void actualizarBebidas(){
        obtenerTodasLasBebidas();


        recyclerViewBebida.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adaptadorBebida = new RecyclerViewAdapterBebida(bebidas);
        recyclerViewBebida.setAdapter(adaptadorBebida);

       /*

       ATENCIÓN: LINEAS DE CODIGO EXPLOSIVAS:
        Toast toast = Toast.makeText(getApplicationContext(), bebidas.size(), Toast.LENGTH_SHORT);
        toast.show();
*/
    }

}
