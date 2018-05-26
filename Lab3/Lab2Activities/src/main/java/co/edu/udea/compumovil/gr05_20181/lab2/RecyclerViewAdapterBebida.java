package co.edu.udea.compumovil.gr05_20181.lab2;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.edu.udea.compumovil.gr05_20181.lab2.data.bebida;

public class RecyclerViewAdapterBebida extends RecyclerView.Adapter<RecyclerViewAdapterBebida.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombre, ingredientes, precio;
        ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);
            this.nombre = (TextView) itemView.findViewById(R.id.bebida_nombre);
            this.ingredientes = (TextView) itemView.findViewById(R.id.bebida_ingredientes);
            this.precio = (TextView) itemView.findViewById(R.id.bebida_precio);
            this.foto = (ImageView) itemView.findViewById(R.id.bebida_imagen);
        }
    }

    public List<bebida> bebidasLista;

    public RecyclerViewAdapterBebida(List<bebida> bebidasLista){
        this.bebidasLista = bebidasLista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bebida, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Uri uri = Uri.parse(bebidasLista.get(position).getFoto());
        if(uri != null){
            holder.nombre.setText("Nombre: " + bebidasLista.get(position).getNombre());
            holder.ingredientes.setText("Ingredientes: " + bebidasLista.get(position).getIngredientes());
            holder.precio.setText("Precio: " + bebidasLista.get(position).getPrecio().toString());
            holder.foto.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount() {
        return bebidasLista.size();
    }
}
