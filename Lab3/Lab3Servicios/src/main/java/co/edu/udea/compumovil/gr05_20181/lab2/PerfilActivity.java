package co.edu.udea.compumovil.gr05_20181.lab2;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PerfilActivity extends AppCompatActivity {

    private ImageView imagen;
    private TextView nombre, correo;
    private String nombreUsuario, apellidoUsuario, correoUsuario, fotoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfiguracionesActivity configuracionesActivity = new ConfiguracionesActivity();
        setContentView(R.layout.activity_perfil);
        getExtras();

        imagen = findViewById(R.id.imagen);
        nombre = findViewById(R.id.campoNombre2);
        correo = findViewById(R.id.campoCorreo2);
        String Usuario = getIntent().getStringExtra("nombreUsuario") + " " + getIntent().getExtras();
        nombre.setText(nombreUsuario + " " +  apellidoUsuario);
        correo.setText(correoUsuario);
        Uri uri = Uri.parse(fotoUsuario);
        Glide.with(getApplicationContext())
                .load(uri)
                .into(imagen);
    }

    public void getExtras(){
        Bundle bundle = getIntent().getExtras();
        this.nombreUsuario = bundle.getString("NombreUsuario");
        this.apellidoUsuario = bundle.getString("apellidoUsuario");
        this.correoUsuario = bundle.getString("correoUsuario");
        this.fotoUsuario = bundle.getString("fotoUsuario");
    }
}
