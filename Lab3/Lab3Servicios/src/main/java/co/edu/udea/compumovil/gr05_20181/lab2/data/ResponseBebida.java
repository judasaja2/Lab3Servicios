package co.edu.udea.compumovil.gr05_20181.lab2.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBebida {
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("ingredientes")
    @Expose
    private String ingredientes;
    @SerializedName("precio")
    @Expose
    private Integer precio;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("id")
    @Expose
    private Integer id;

    public ResponseBebida(String nombre, String ingredientes, Integer precio, String foto) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
