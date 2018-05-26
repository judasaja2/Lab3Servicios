package co.edu.udea.compumovil.gr05_20181.lab2;

import android.Manifest;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr05_20181.lab2.data.ResponsePlato;
import gun0912.tedbottompicker.TedBottomPicker;
import retrofit2.Call;

import static co.edu.udea.compumovil.gr05_20181.lab2.LoginActivity.mApiService;

public class AddPlatosActivity extends AppCompatActivity {

    private Button botonGaleria, botonRegistrar;
    private static NumberPicker pickerHorario;
    private static EditText campoPrecio, campoNombre, campoIngredientes;
    private Uri selectedUri = null;
    private ImageView iv_image;
    private TextView etiqueta;
    private static RadioButton botonPlatoFuerte, botonEntrada;
    private static final String RESUME_KEY = "resume";
    private static final String FOTO_KEY = "foto";
    private static String datosrRecuperados;
    private static String datosrRecuperados2;
    private static CheckBox rbm, rbt, rbn;
    private static ResponsePlato plato;
    private static List<ResponsePlato> platos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_platos);
        botonGaleria = (Button) findViewById(R.id.botonGaleriaPlato);
        iv_image = (ImageView) findViewById(R.id.imageViewPlato);
        etiqueta = (TextView) findViewById(R.id.tiempoCoccion);
        campoPrecio = (EditText) findViewById(R.id.editTextPrecioPlato);
        campoIngredientes = (EditText) findViewById(R.id.editTextIngredientesPlato);
        campoNombre = (EditText) findViewById(R.id.editTextNombrePlato);
        botonRegistrar = (Button) findViewById(R.id.botonRegistrar);
        pickerHorario = (NumberPicker) findViewById(R.id.numberPicker);
        rbt = (CheckBox) findViewById(R.id.tardeRb);
        rbm = (CheckBox) findViewById(R.id.mañanaRb);
        rbn = (CheckBox) findViewById(R.id.nocheRb);
        botonEntrada = (RadioButton) findViewById(R.id.radioButton);
        botonPlatoFuerte = (RadioButton) findViewById(R.id.radioButton2);
        pickerHorario.setWrapSelectorWheel(true);
        etiqueta.setEnabled(false);
        pickerHorario.setMinValue(1);
        pickerHorario.setMaxValue(14);
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
        ////////////////////////////cuadroDatos.setMovementMethod(new ScrollingMovementMethod());
        rbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbn.setChecked(false);
                rbt.setChecked(false);
            }
        });
        rbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbm.setChecked(false);
                rbt.setChecked(false);
            }
        });
        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbm.setChecked(false);
                rbn.setChecked(false);
            }
        });
        botonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingleShowButton();
            }
        });
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PeticionPlatoPost().execute();
            }
        });
    }

    private void setSingleShowButton() {
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Debe aceptar los permisos")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(RESUME_KEY, datosrRecuperados);
        savedInstanceState.putString(FOTO_KEY, datosrRecuperados2);
        super.onSaveInstanceState(savedInstanceState);
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
            Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    };

    public static class PeticionPlatoPost extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String nombre, foto, ingredientes, horario = null, tipo = null, tiempo;
            Integer precio;
            nombre = String.valueOf(campoNombre.getText());
            precio = Integer.parseInt(String.valueOf(campoPrecio.getText()));
            ingredientes = String.valueOf(campoIngredientes.getText());
            foto = datosrRecuperados2;
            if (botonEntrada.isChecked())
                tipo = botonEntrada.getText().toString();
            else if (botonPlatoFuerte.isChecked())
                tipo = botonPlatoFuerte.getText().toString();
            if (rbm.isChecked())
                horario = rbm.getText().toString();
            else if (rbn.isChecked())
                horario = rbn.getText().toString();
            else if (rbt.isChecked())
                horario = rbt.getText().toString();
            tiempo = String.valueOf(pickerHorario.getValue());
            try {
                Call<ResponsePlato> response = mApiService.postPlato(nombre.trim(), ingredientes.trim(),
                        horario.trim(), tipo.trim(), tiempo.trim(), precio, foto);
                response.execute();
            } catch (IOException e) {
                Log.e("ERROR: ", e.toString());
            }
            return null;
        }
    }

}
