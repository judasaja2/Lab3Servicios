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
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr05_20181.lab2.data.ResponseBebida;
import retrofit2.Call;

import static co.edu.udea.compumovil.gr05_20181.lab2.LoginActivity.mApiService;

public class BebidasActivity extends AppCompatActivity {

    private Menu menu;
    private Button buttonBuscarBebida;
    private FloatingActionButton fabAddBebida, fabActualizarBebida;
    private EditText editTextBuscarBebida;
    private static RecyclerView recyclerViewBebida;
    private static RecyclerViewAdapterBebida adaptadorBebida;
    private static List<ResponseBebida> bebidas;
    private static List<ResponseBebida> bebidasBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);

        fabAddBebida = (FloatingActionButton) findViewById(R.id.fabAddBebida);
        fabAddBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddBebidasActivity.class);
                startActivity(intent);
            }
        });
        editTextBuscarBebida = findViewById(R.id.editTextBuscarBebida);
        buttonBuscarBebida = findViewById(R.id.buttonBuscarBebida);
        buttonBuscarBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarBebidas("Search");
            }
        });
        fabActualizarBebida = findViewById(R.id.fabActualizarBebida);
        fabActualizarBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarBebidas("All");
            }
        });
        recyclerViewBebida = (RecyclerView) findViewById(R.id.recycler_bebidas);
        mostrarBebidas("All");
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

    private void mostrarBebidas(final String Option) {
        new PeticionBebidaGet().execute();
        new Handler().postDelayed(new Runnable(){
            public void run(){
                recyclerViewBebida.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                if(Option.equals("All")) {
                    adaptadorBebida = new RecyclerViewAdapterBebida(bebidas);
                } else if(Option.equals("Search")){
                    buscarBebidas(editTextBuscarBebida.getText().toString());
                    adaptadorBebida = new RecyclerViewAdapterBebida(bebidasBusqueda);
                }
                recyclerViewBebida.setAdapter(adaptadorBebida);
            };
        }, 250);
    }

    private void buscarBebidas(final String termino){
        bebidasBusqueda = new ArrayList<>();
        for(ResponseBebida bebida : bebidas){
            if(bebida.getNombre().toLowerCase().equals(termino.toLowerCase())){
                bebidasBusqueda.add(bebida);
            }
        }
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
