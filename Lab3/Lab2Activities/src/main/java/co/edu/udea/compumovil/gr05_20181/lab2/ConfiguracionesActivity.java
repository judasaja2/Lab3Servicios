package co.edu.udea.compumovil.gr05_20181.lab2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import co.edu.udea.compumovil.gr05_20181.lab2.data.dbHelper;
import co.edu.udea.compumovil.gr05_20181.lab2.data.usuarioContract;

public class ConfiguracionesActivity extends AppCompatActivity {

    public static final String KEY_PREF_NOMBRE = "nombre";
    private static final String KEY_PREF_APELLIDO = "apellido";
    private static final String KEY_PREF_FOTO = "foto";
    private static final String KEY_PREF_CONTRASEÑA = "contraseña";
    private static final String KEY_PREF_SESION = "mantener_sesion";
    private static final String KEY_PREF_NOTIFICACIONES = "autorizar_notificaciones";
    private static final String KEY_PREF_NOTIFICACIONES_SONIDO = "sonido_notificaciones";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new ConfiguracionesFragment())
                .commit();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final dbHelper db = new dbHelper(getApplicationContext());;
        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if(key.equals(ConfiguracionesActivity.KEY_PREF_NOMBRE)){
                    db.actualizarPerfil(usuarioContract.usuarioEntry.NOMBRE, getKeyPrefNombre(getApplicationContext()), getIntent().getStringExtra("Usuario"));
                } else if(key.equals(ConfiguracionesActivity.KEY_PREF_APELLIDO)){
                    db.actualizarPerfil(usuarioContract.usuarioEntry.APELLIDO, getKeyPrefApellido(getApplicationContext()), getIntent().getStringExtra("Usuario"));
                } else if(key.equals(ConfiguracionesActivity.KEY_PREF_FOTO)){
                    db.actualizarPerfil(usuarioContract.usuarioEntry.FOTO, getKeyPrefFoto(getApplicationContext()), getIntent().getStringExtra("Usuario"));
                } else if(key.equals(ConfiguracionesActivity.KEY_PREF_CONTRASEÑA)){
                    db.actualizarPerfil(usuarioContract.usuarioEntry.CONTRASEÑA, getKeyPrefContraseña(getApplicationContext()), getIntent().getStringExtra("Usuario"));
                } else if(key.equals(ConfiguracionesActivity.KEY_PREF_SESION)){

                } else if(key.equals(ConfiguracionesActivity.KEY_PREF_NOTIFICACIONES)){

                } else if(key.equals(ConfiguracionesActivity.KEY_PREF_NOTIFICACIONES_SONIDO)){

                }
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(prefListener);

    }


    public String getKeyPrefNombre(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(ConfiguracionesActivity.KEY_PREF_NOMBRE, "");
    }

    public String getKeyPrefApellido(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(ConfiguracionesActivity.KEY_PREF_APELLIDO, "");
    }

    public String getKeyPrefFoto(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(ConfiguracionesActivity.KEY_PREF_FOTO, "");
    }

    public String getKeyPrefContraseña(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(ConfiguracionesActivity.KEY_PREF_CONTRASEÑA, "");
    }

    public boolean getSesion(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(ConfiguracionesActivity.KEY_PREF_SESION, false);
    }

    public void firstTimeRun(String nombre, String apellido, String foto, String contraseña, Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConfiguracionesActivity.KEY_PREF_NOMBRE, nombre);
        editor.putString(ConfiguracionesActivity.KEY_PREF_APELLIDO, apellido);
        editor.putString(ConfiguracionesActivity.KEY_PREF_FOTO, foto);
        editor.putString(ConfiguracionesActivity.KEY_PREF_CONTRASEÑA, contraseña);
        editor.commit();
    }

}
