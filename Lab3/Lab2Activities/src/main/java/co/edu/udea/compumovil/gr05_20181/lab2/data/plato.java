package co.edu.udea.compumovil.gr05_20181.lab2.data;

import android.content.ContentValues;

public class plato {
    private String nombre;
    private String horario;
    private String tipo;
    private String tiempo;
    private String foto; //realmente se guarda la uri
    private Float precio;
    private String ingredientes;

    public plato(String nomb, String hora, String tip, String tiemp, String fot, Float pre, String ingre){
        this.setNombre(nomb);
        this.setHorario(hora);
        this.setTipo(tip);
        this.setTiempo(tiemp);
        this.setFoto(fot);
        this.setPrecio(pre);
        this.setIngredientes(ingre);
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
        values.put(platoContract.platoEntry.INGREDIENTES, ingredientes);
        values.put(platoContract.platoEntry.NOMBRE, nombre);
        values.put(platoContract.platoEntry.PRECIO, precio);
        values.put(platoContract.platoEntry.HORARIO, horario);
        values.put(platoContract.platoEntry.TIEMPO, tiempo);
        values.put(platoContract.platoEntry.TIPO, tipo);
        values.put(platoContract.platoEntry.FOTO, foto);
        return values;
    }


}
