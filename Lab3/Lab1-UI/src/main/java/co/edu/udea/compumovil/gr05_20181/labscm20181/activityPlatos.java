package co.edu.udea.compumovil.gr05_20181.labscm20181;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.w3c.dom.Text;

import java.util.ArrayList;

import gun0912.tedbottompicker.TedBottomPicker;

public class activityPlatos extends AppCompatActivity {
    private Menu menu;
    private Button botonGaleria, botonRegistrar;
    private NumberPicker pickerHorario;
    private EditText campoPrecio, campoNombre, campoIngredientes;
    ArrayList<Uri> selectedUriList;
    private Uri selectedUri = null;
    private ViewGroup mSelectedImagesContainer;
    private ImageView iv_image;
    private TextView cuadroDatos, etiqueta;
    private RadioGroup grupoRadios;
    private RadioButton botonPlatoFuerte, botonEntrada;
    private static final String RESUME_KEY = "resume";
    private static final String FOTO_KEY = "foto";
    private String datosrRecuperados;
    private String datosrRecuperados2;
    CheckBox rbm, rbt, rbn;
    public RequestManager mGlideRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platos);
        botonGaleria = (Button) findViewById(R.id.botonGaleriaPlato);
        iv_image = (ImageView) findViewById(R.id.imageViewPlato);
        etiqueta = (TextView) findViewById(R.id.tiempoCoccion);
        campoPrecio = (EditText) findViewById(R.id.editTextPrecioPlato);
        campoIngredientes = (EditText) findViewById(R.id.editTextIngredientesPlato);
        campoNombre = (EditText) findViewById(R.id.editTextNombrePlato);
        grupoRadios = (RadioGroup) findViewById(R.id.grupoRadios);
        botonRegistrar = (Button) findViewById(R.id.botonRegistrar);
        pickerHorario = (NumberPicker) findViewById(R.id.numberPicker);
        rbt = (CheckBox) findViewById(R.id.tardeRb);
        rbm = (CheckBox) findViewById(R.id.mañanaRb);
        rbn = (CheckBox) findViewById(R.id.nocheRb);
        cuadroDatos = (TextView) findViewById(R.id.mostrarDatos);
        botonEntrada = (RadioButton) findViewById(R.id.radioButton);
        botonPlatoFuerte = (RadioButton) findViewById(R.id.radioButton2);
        pickerHorario.setWrapSelectorWheel(true);
        etiqueta.setEnabled(false);
        if (savedInstanceState != null) {
            datosrRecuperados = savedInstanceState.getString(RESUME_KEY);
            datosrRecuperados2 = savedInstanceState.getString(FOTO_KEY);

            cuadroDatos.setText(datosrRecuperados);
            if (datosrRecuperados2 != null)
                selectedUri = Uri.parse(datosrRecuperados2);
            if (selectedUri != null) {
                Glide.with(activityPlatos.this)
                        .load(selectedUri)
                        .into(iv_image);
            }
        }
        cuadroDatos.setMovementMethod(new ScrollingMovementMethod());
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

        pickerHorario.setMinValue(1);
        pickerHorario.setMaxValue(14);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuadroDatos.setText(cuadroDatos.getText() + campoNombre.getHint().toString() + ": " + campoNombre.getText() + "\n");
                cuadroDatos.setText(cuadroDatos.getText() + campoPrecio.getHint().toString() + ": " + campoPrecio.getText() + "\n");
                cuadroDatos.setText(cuadroDatos.getText() + campoIngredientes.getHint().toString() + ": " + campoIngredientes.getText() + "\n");
                if (botonEntrada.isSelected())
                    cuadroDatos.setText(cuadroDatos.getText() + botonEntrada.getText().toString() + "\n");
                else if (botonPlatoFuerte.isSelected())
                    cuadroDatos.setText(cuadroDatos.getText() + botonPlatoFuerte.getText().toString() + "\n");
                if (rbm.isChecked())
                    cuadroDatos.setText(cuadroDatos.getText() + "horario" + ": " + rbm.getText().toString() + "\n");
                else if (rbn.isChecked())
                    cuadroDatos.setText(cuadroDatos.getText() + "horario" + rbn.getText().toString() + "\n");
                else if (rbt.isChecked())
                    cuadroDatos.setText(cuadroDatos.getText() + "horario" + rbt.getText().toString() + "\n");
                String tiempo = String.valueOf(pickerHorario.getValue());
                cuadroDatos.setText(cuadroDatos.getText() + etiqueta.getText().toString() + ": " + tiempo + "\n\n");
                datosrRecuperados = String.valueOf(cuadroDatos.getText());
                guardarPreferencias(cuadroDatos.getText().toString());
            }
        });
        cargarPreferencias(cuadroDatos);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        datosrRecuperados = String.valueOf(cuadroDatos.getText());
        savedInstanceState.putString(RESUME_KEY, datosrRecuperados);
        savedInstanceState.putString(FOTO_KEY, datosrRecuperados2);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void setSingleShowButton() {
        TedPermission.with(activityPlatos.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Debe aceptar los permisos")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
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

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(activityPlatos.this)
                    .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                        @Override
                        public void onImageSelected(Uri uri) {
                            selectedUri = uri;
                            datosrRecuperados2 = String.valueOf(selectedUri);
                            Glide.with(activityPlatos.this)
                                    .load(uri)
                                    .into(iv_image);
                        }
                    })
                    .create();
            tedBottomPicker.show(getSupportFragmentManager());
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(activityPlatos.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private void guardarPreferencias(String campoDato) {
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Platos", campoDato);
        editor.commit();
    }

    private void cargarPreferencias(TextView campoDato) {
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dato = preferences.getString("Platos", "");
        campoDato.setText(dato);
    }
}
