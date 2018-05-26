package co.edu.udea.compumovil.gr05_20181.lab2.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePlato {
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("ingredientes")
    @Expose
    private String ingredientes;
    @SerializedName("horario")
    @Expose
    private String horario;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("tiempo")
    @Expose
    private String tiempo;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("precio")
    @Expose
    private Integer precio;
    @SerializedName("id")
    @Expose
    private Integer id;

    public ResponsePlato(String nombre, String ingredientes, String horario, String tipo, String tiempo, Integer precio, String foto) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.horario = horario;
        this.tipo = tipo;
        this.tiempo = tiempo;
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

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
