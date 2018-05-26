package co.edu.udea.compumovil.gr05_20181.lab2;

import android.Manifest;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr05_20181.lab2.data.dbHelper;
import co.edu.udea.compumovil.gr05_20181.lab2.data.usuario;
import gun0912.tedbottompicker.TedBottomPicker;

import static android.util.Log.println;

public class RegistroActivity extends AppCompatActivity {

    private ImageButton botonImagen;
    private Button botonGuardar;
    String datosRecuperados;
    private EditText campoNombre, campoApellido, campoContraseña,campoCorreo;


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
//hola@hotmail.com contraseña: carla
               String foto = datosRecuperados;
               String nombre, correo, contraseña, apellido;
               nombre = String.valueOf(campoNombre.getText());
               apellido = String.valueOf(campoApellido.getText());
               correo = String.valueOf(campoCorreo.getText());
               contraseña = String.valueOf(campoContraseña.getText());
               usuario user = new usuario(nombre, contraseña, apellido, correo, foto);
               dbHelper db = new dbHelper(getApplicationContext());
               try  {
                 //  println(Log.ERROR,"MYTAG","Error "+contraseña);
                   db.guardarUsuario(user);
               }
               catch (Exception e){
                  println(Log.ERROR,"MYTAG","Error "+e);
               }

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
                         /*   Glide.with(BebidasActivity.this)
                                    .load(uri)
                                    .into(iv_image);
                                    */
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


}

