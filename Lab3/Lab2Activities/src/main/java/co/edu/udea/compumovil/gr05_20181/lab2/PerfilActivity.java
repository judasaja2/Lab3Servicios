package co.edu.udea.compumovil.gr05_20181.lab2;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import co.edu.udea.compumovil.gr05_20181.lab2.data.dbHelper;
import co.edu.udea.compumovil.gr05_20181.lab2.data.usuarioContract;

import static android.util.Log.println;

public class PerfilActivity extends AppCompatActivity {

    ImageView imagen;
    TextView nombre, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfiguracionesActivity configuracionesActivity = new ConfiguracionesActivity();
        setContentView(R.layout.activity_perfil);
        imagen = findViewById(R.id.imagen);
        nombre = findViewById(R.id.campoNombre2);
        correo = findViewById(R.id.campoCorreo2);
        dbHelper db = new dbHelper(getApplicationContext());
        String Usuario= getIntent().getStringExtra("Usuario");
        Cursor c = db.obtenerUsuarioPorCorreo(Usuario);
        c.moveToNext();
        nombre.setText(configuracionesActivity.getKeyPrefNombre(getApplicationContext()) + " " + configuracionesActivity.getKeyPrefApellido(getApplicationContext()));
        correo.setText(Usuario);
        Uri uri = Uri.parse(configuracionesActivity.getKeyPrefFoto(getApplicationContext()));
        Glide.with(PerfilActivity.this)
                .load(uri)
                .into(imagen);
    }
}
