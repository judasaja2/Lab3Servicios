package co.edu.udea.compumovil.gr05_20181.lab2.data;

import android.provider.BaseColumns;

public class platoContract {


    public static abstract class platoEntry implements BaseColumns {
        public static final String TABLE_NAME = "plato";
        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String HORARIO= "horario";
        public static final String TIPO = "tipo";
        public static final String TIEMPO = "tiempo";
        public static final String FOTO = "foto";
        public static final String PRECIO = "precio";
        public static final String INGREDIENTES = "ingredientes";



    }


}
