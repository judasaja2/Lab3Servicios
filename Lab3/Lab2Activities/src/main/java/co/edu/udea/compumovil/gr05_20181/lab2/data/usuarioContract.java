package co.edu.udea.compumovil.gr05_20181.lab2.data;

import android.provider.BaseColumns;

public class usuarioContract {

    public static abstract class usuarioEntry implements BaseColumns {
        public static final String TABLE_NAME = "usuario";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String CORREO = "correo";
        public static final String APELLIDO = "apellido";
        public static final String CONTRASEÑA = "contraseña";
        public static final String FOTO = "foto";

    }
}
