package co.edu.udea.compumovil.gr05_20181.lab2.data;

import android.content.ContentValues;

/**
 * Created by johanc.suarez on 10/04/18.
 */

public class bebida {

    private String foto;
    private String nombre;
    private Float precio;
    private String ingredientes;

   public bebida(String nomb, String fot, Float pre, String ingre){
       this.setNombre(nomb);
       this.setFoto(fot);
       this.setPrecio(pre);
       this.setIngredientes(ingre);
   }


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(bebidaContract.bebidaEntry.NOMBRE, nombre);
        values.put(bebidaContract.bebidaEntry.PRECIO, precio);
        values.put(bebidaContract.bebidaEntry.FOTO, foto);
        values.put(bebidaContract.bebidaEntry.INGREDIENTES, ingredientes);

        return values;
    }
}
