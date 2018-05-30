package co.edu.udea.compumovil.gr05_20181.lab2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;

import co.edu.udea.compumovil.gr05_20181.lab2.data.ResponseUsuario;
import co.edu.udea.compumovil.gr05_20181.lab2.data.remote.APIService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity{

    private static EditText mPasswordView, mEmailView;
    private static ResponseUsuario usuarioLogueado;
    private static Boolean comprobacion;
    private static Boolean busquedaFinalizada;
    private Retrofit retrofit;
    public static APIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mLogin = (Button) findViewById(R.id.login_ingresar);
        mLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(view);
            }
        });

        Button mRegistro = (Button) findViewById(R.id.login_registrar);
        mRegistro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(intent);
            }
        });

        final String url = "http://192.168.1.73:3000/";

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(APIService.class);
    }

    private void attemptLogin(final View view) {
        new PeticionUsuarioGet().execute();
        new Handler().postDelayed(new Runnable(){
            public void run(){
                if (comprobacion) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("nombreUsuario", usuarioLogueado.getNombre());
                    intent.putExtra("apellidoUsuario", usuarioLogueado.getApellido());
                    intent.putExtra("correoUsuario", usuarioLogueado.getCorreo());
                    intent.putExtra("fotoUsuario", usuarioLogueado.getFoto());
                    startActivity(intent);
                } else {
                    Snackbar.make(view, "Correo o contraseña vacíos o incorrectos.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            };
        }, 250);
    }

    public static class PeticionUsuarioGet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            comprobacion = false;
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();
            try {
                Call<List<ResponseUsuario>> response = mApiService.getUsers();
                for (ResponseUsuario usuario : response.execute().body()){
                    if(usuario.getCorreo().equals(email) && usuario.getContrasena().equals(password)){
                        usuarioLogueado = usuario;
                        comprobacion = true;
                    }
                }
            } catch(IOException e){
                Log.e("ERROR: ", e.toString());
            }
            return null;
        }
    }

}

