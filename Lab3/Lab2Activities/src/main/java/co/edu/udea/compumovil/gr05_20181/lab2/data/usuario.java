package co.edu.udea.compumovil.gr05_20181.lab2.data;

import android.content.ContentValues;
import android.database.Cursor;

public class usuario {

    private String nombre;
    private String correo;
    private String contraseña;
    private String apellido;
    private String foto; //realmente se guarda la uri

    public usuario(String n, String co, String ape, String cor, String f){
        this.setNombre(n);
        this.setCorreo(cor);
        this.setApellido(ape);
        this.setContraseña(co);
        this.setFoto(f);

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(usuarioContract.usuarioEntry.CORREO, correo);
        values.put(usuarioContract.usuarioEntry.NOMBRE, nombre);
        values.put(usuarioContract.usuarioEntry.APELLIDO, apellido);
        values.put(usuarioContract.usuarioEntry.FOTO, foto);
        values.put(usuarioContract.usuarioEntry.CONTRASEÑA, contraseña);
        return values;
    }




    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


}
