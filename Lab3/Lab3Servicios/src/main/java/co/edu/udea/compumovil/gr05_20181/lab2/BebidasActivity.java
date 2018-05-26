package co.edu.udea.compumovil.gr05_20181.lab2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr05_20181.lab2.data.ResponseBebida;
import retrofit2.Call;

import static co.edu.udea.compumovil.gr05_20181.lab2.LoginActivity.mApiService;

public class BebidasActivity extends AppCompatActivity {

    private Menu menu;
    private static RecyclerView recyclerViewBebida;
    private static RecyclerViewAdapterBebida adaptadorBebida;
    private static List<ResponseBebida> bebidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddBebida);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddBebidasActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewBebida = (RecyclerView) findViewById(R.id.recycler_bebidas);
        actualizarBebidas();
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
            //campoIngredientes.setText("");
        } else if (id == R.id.salir) {
            System.exit(1);
        } else if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return true;
    }

    private void actualizarBebidas() {
        new PeticionBebidaGet().execute();
        new Handler().postDelayed(new Runnable(){
            public void run(){
                recyclerViewBebida.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adaptadorBebida = new RecyclerViewAdapterBebida(bebidas);
                recyclerViewBebida.setAdapter(adaptadorBebida);
            };
        }, 250);
    }

    public static class PeticionBebidaGet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            bebidas = new ArrayList<>();
            try {
                Call<List<ResponseBebida>> response = mApiService.getBebidas();
                for (ResponseBebida bebida : response.execute().body()) {
                    bebida = new ResponseBebida(bebida.getNombre(), bebida.getIngredientes(), bebida.getPrecio(), bebida.getFoto());
                    bebidas.add(bebida);
                }
            } catch(IOException e){
                Log.e("ERROR: ", e.toString());
            }
            return null;
        }
    }

}
