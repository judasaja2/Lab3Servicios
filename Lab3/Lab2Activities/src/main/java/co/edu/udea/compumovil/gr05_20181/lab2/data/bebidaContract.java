package co.edu.udea.compumovil.gr05_20181.lab2.data;

import android.provider.BaseColumns;

/**
 * Created by johanc.suarez on 10/04/18.
 */

public class bebidaContract {

    public static abstract class bebidaEntry implements BaseColumns {
        public static final String TABLE_NAME = "bebida";
        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String PRECIO = "precio";
        public static final String INGREDIENTES = "ingredientes";
        public static final String FOTO = "foto";

    }

}
