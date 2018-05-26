package co.edu.udea.compumovil.gr05_20181.lab2;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.edu.udea.compumovil.gr05_20181.lab2.data.ResponsePlato;

public class RecyclerViewAdapterPlato extends RecyclerView.Adapter<RecyclerViewAdapterPlato.ViewHolder> {

public static class ViewHolder extends RecyclerView.ViewHolder{
    private TextView nombre, ingredientes, precio, tiempo, tipo, horario;
    ImageView foto;

    public ViewHolder(View itemView) {
        super(itemView);
        this.nombre = (TextView) itemView.findViewById(R.id.plato_nombre);
        this.ingredientes = (TextView) itemView.findViewById(R.id.plato_ingredientes);
        this.tipo = (TextView) itemView.findViewById(R.id.plato_tipo);
        this.horario = (TextView) itemView.findViewById(R.id.plato_horario);
        this.tiempo = (TextView) itemView.findViewById(R.id.plato_tiempo);
        this.precio = (TextView) itemView.findViewById(R.id.plato_precio);
        this.foto = (ImageView) itemView.findViewById(R.id.plato_imagen);
    }
}

    public List<ResponsePlato> platosLista;

    public RecyclerViewAdapterPlato(List<ResponsePlato> platosLista){
        this.platosLista = platosLista;
    }

    @Override
    public RecyclerViewAdapterPlato.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plato, parent, false);
        RecyclerViewAdapterPlato.ViewHolder viewHolder = new RecyclerViewAdapterPlato.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterPlato.ViewHolder holder, int position) {
        Uri uri = Uri.parse(platosLista.get(position).getFoto());
        if(uri != null){
            holder.nombre.setText("Nombre: " + platosLista.get(position).getNombre());
            holder.ingredientes.setText("Ingredientes: " + platosLista.get(position).getIngredientes());
            holder.tipo.setText("Tipo: " + platosLista.get(position).getTipo());
            holder.horario.setText("Horario: " + platosLista.get(position).getHorario());
            holder.tiempo.setText("Tiempo: " + platosLista.get(position).getTiempo());
            holder.precio.setText("Precio: " + platosLista.get(position).getPrecio().toString());
            holder.foto.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount() {
        return platosLista.size();
    }
}