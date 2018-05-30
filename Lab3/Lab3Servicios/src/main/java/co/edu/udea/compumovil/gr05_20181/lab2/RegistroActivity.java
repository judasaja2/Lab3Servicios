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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr05_20181.lab2.data.ResponseUsuario;
import gun0912.tedbottompicker.TedBottomPicker;
import retrofit2.Call;

import static co.edu.udea.compumovil.gr05_20181.lab2.LoginActivity.mApiService;

public class RegistroActivity extends AppCompatActivity {

    private ImageButton botonImagen;
    private Button botonGuardar;
    private static String datosRecuperados;
    private static ImageView iv_image;
    private static EditText campoNombre, campoApellido, campoContraseña,campoCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        botonGuardar=findViewById(R.id.botonRegistrarse);
        botonImagen = findViewById(R.id.botonImagen);
        campoNombre=findViewById(R.id.campoNombre2);
        campoApellido=findViewById(R.id.campoApellido);
        campoContraseña=findViewById(R.id.campoContraseña);
        campoCorreo=findViewById(R.id.campoEmail);



       botonGuardar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               new PeticionUsuarioPost().execute();
           }
       });

       botonImagen.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               setSingleShowButton();
           }
       });

    }

    private void setSingleShowButton() {
        TedPermission.with(RegistroActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Debe aceptar los permisos")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(RegistroActivity.this)
                    .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                        @Override
                        public void onImageSelected(Uri uri) {
                           Uri selectedUri = uri;
                            datosRecuperados = String.valueOf(selectedUri);
                        }
                    })
                    .create();
            tedBottomPicker.show(getSupportFragmentManager());
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(RegistroActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    public static class PeticionUsuarioPost extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String nombre, apellido, correo, contraseña, foto;
            nombre = String.valueOf(campoNombre.getText());
            apellido = String.valueOf(campoApellido.getText());
            correo = String.valueOf(campoCorreo.getText());
            contraseña = String.valueOf(campoContraseña.getText());
            foto = datosRecuperados;
            try {
                Call<ResponseUsuario> response = mApiService.postUser(nombre.trim(), apellido.trim(), correo.trim(), contraseña.trim(), foto);
                response.execute();
            } catch (IOException e) {
                Log.e("ERROR: ", e.toString());
            }
            return null;
        }
    }

}

