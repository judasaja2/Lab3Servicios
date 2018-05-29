package co.edu.udea.compumovil.gr05_20181.lab2;

import android.Manifest;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.ArrayList;

import co.edu.udea.compumovil.gr05_20181.lab2.data.ResponseBebida;
import gun0912.tedbottompicker.TedBottomPicker;
import retrofit2.Call;

import static co.edu.udea.compumovil.gr05_20181.lab2.LoginActivity.mApiService;

public class AddBebidasActivity extends AppCompatActivity {

    private static final String RESUME_KEY = "resume";
    private static final String FOTO_KEY = "foto";
    private static String datosrRecuperados, datosrRecuperados2;
    private static Uri selectedUri = null;
    private static ImageView iv_image;
    private static EditText campoNombre, campoPrecio, campoIngredientes;
    private static Button botonGaleria, botonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bebidas);
        campoNombre = (EditText) findViewById(R.id.editTextNombreBebida);
        campoPrecio = (EditText) findViewById(R.id.editTextPrecioBebida);
        campoIngredientes = (EditText) findViewById(R.id.editTextIngredientesBebida);
        botonGaleria = (Button) findViewById(R.id.botonGaleriaBebida);
        botonRegistrar = (Button) findViewById(R.id.botonRegistrarBebidas);
        iv_image = (ImageView) findViewById(R.id.imageViewBebida);
        if (savedInstanceState != null) {
            datosrRecuperados = savedInstanceState.getString(RESUME_KEY);
            datosrRecuperados2 = savedInstanceState.getString(FOTO_KEY);
            if (datosrRecuperados2 != null)
                selectedUri = Uri.parse(datosrRecuperados2);
            if (selectedUri != null) {
                Glide.with(getApplicationContext())
                        .load(selectedUri)
                        .into(iv_image);
            }
        }
        botonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingleShowButton();
            }
        });
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddBebidasActivity.PeticionBebidaPost().execute();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(RESUME_KEY, datosrRecuperados);
        savedInstanceState.putString(FOTO_KEY, datosrRecuperados2);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void setSingleShowButton() {
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Debe aceptar los permisos")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(getApplicationContext())
                    .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                        @Override
                        public void onImageSelected(Uri uri) {
                            selectedUri = uri;
                            datosrRecuperados2 = String.valueOf(selectedUri);
                            Glide.with(getApplicationContext())
                                    .load(uri)
                                    .into(iv_image);
                        }
                    })
                    .create();
            tedBottomPicker.show(getSupportFragmentManager());
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    public static class PeticionBebidaPost extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String nombre, foto, ingredientes;
            Integer precio;
            nombre = String.valueOf(campoNombre.getText());
            ingredientes = String.valueOf(campoIngredientes.getText());
            precio = Integer.parseInt(String.valueOf(campoPrecio.getText()));
            foto = datosrRecuperados2;
            try {
                Call<ResponseBebida> response = mApiService.postBebida(nombre.trim(), ingredientes.trim(), precio, foto);
                response.execute();
            } catch (IOException e) {
                Log.e("ERROR: ", e.toString());
            }
            return null;
        }
    }

}
