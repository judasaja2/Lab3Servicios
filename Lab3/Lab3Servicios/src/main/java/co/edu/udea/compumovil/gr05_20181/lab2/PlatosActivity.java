package co.edu.udea.compumovil.gr05_20181.lab2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr05_20181.lab2.data.ResponsePlato;
import retrofit2.Call;

import static co.edu.udea.compumovil.gr05_20181.lab2.LoginActivity.mApiService;

public class PlatosActivity extends AppCompatActivity {

    private Menu menu;
    private static RecyclerView recyclerViewPlato;
    private static RecyclerViewAdapterPlato adaptadorPlato;
    private static ResponsePlato plato;
    private static List<ResponsePlato> platos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platos);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddPlato);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPlatosActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewPlato = (RecyclerView) findViewById(R.id.recycler_platos);
        actualizarPlatos();
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
            //campoNombre.setText("");
            //campoPrecio.setText("");
            //campoIngredientes.setText("");
        } else if (id == R.id.salir) {
            System.exit(1);
        } else if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return true;
    }

    private void actualizarPlatos(){
        new PeticionPlatoGet().execute();
        new Handler().postDelayed(new Runnable(){
            public void run(){
                recyclerViewPlato.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adaptadorPlato = new RecyclerViewAdapterPlato(platos);
                recyclerViewPlato.setAdapter(adaptadorPlato);
            };
        }, 250);
    }

    public static class PeticionPlatoGet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            platos = new ArrayList<>();
            try {
                Call<List<ResponsePlato>> response = mApiService.getPlatos();
                for (ResponsePlato plato : response.execute().body()) {
                    plato = new ResponsePlato(plato.getNombre(), plato.getIngredientes(), plato.getHorario(),
                            plato.getTipo(), plato.getTiempo(), plato.getPrecio(), plato.getFoto());
                    platos.add(plato);
                }
            } catch(IOException e){
                Log.e("ERROR: ", e.toString());
            }
            return null;
        }
    }

}
