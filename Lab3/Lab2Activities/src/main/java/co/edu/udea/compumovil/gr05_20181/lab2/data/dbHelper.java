package co.edu.udea.compumovil.gr05_20181.lab2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Usuario.db";

    public dbHelper(Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + usuarioContract.usuarioEntry.TABLE_NAME + " ("
                + usuarioContract.usuarioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + usuarioContract.usuarioEntry.NOMBRE + " TEXT NOT NULL,"
                + usuarioContract.usuarioEntry.APELLIDO + " TEXT NOT NULL,"
                + usuarioContract.usuarioEntry.CONTRASEÑA + " TEXT NOT NULL,"
                + usuarioContract.usuarioEntry.CORREO + " TEXT NOT NULL,"
                + usuarioContract.usuarioEntry.FOTO + " TEXT NOT NULL,"
                + "UNIQUE (" + usuarioContract.usuarioEntry._ID+ "))");

        sqLiteDatabase.execSQL("CREATE TABLE " + platoContract.platoEntry.TABLE_NAME + " ("
                + platoContract.platoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + platoContract.platoEntry.NOMBRE + " TEXT NOT NULL,"
                + platoContract.platoEntry.FOTO + " TEXT NOT NULL,"
                + platoContract.platoEntry.HORARIO + " TEXT NOT NULL,"
                + platoContract.platoEntry.INGREDIENTES + " TEXT NOT NULL,"
                + platoContract.platoEntry.PRECIO + " FLOAT NOT NULL,"
                + platoContract.platoEntry.TIPO + " TEXT NOT NULL,"
                + platoContract.platoEntry.TIEMPO + " TEXT NOT NULL,"
                + "UNIQUE (" + platoContract.platoEntry._ID+ "))");

        sqLiteDatabase.execSQL("CREATE TABLE " + bebidaContract.bebidaEntry.TABLE_NAME + " ("
                + bebidaContract.bebidaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + bebidaContract.bebidaEntry.NOMBRE + " TEXT NOT NULL,"
                + bebidaContract.bebidaEntry.FOTO + " TEXT NOT NULL,"
                + bebidaContract.bebidaEntry.INGREDIENTES + " TEXT NOT NULL,"
                + bebidaContract.bebidaEntry.PRECIO + " FLOAT NOT NULL,"
                + "UNIQUE (" + platoContract.platoEntry._ID+ "))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public long guardarUsuario(usuario user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                usuarioContract.usuarioEntry.TABLE_NAME,
                null,
                user.toContentValues());

    }

    public long guardarPlato(plato plat) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                platoContract.platoEntry.TABLE_NAME,
                null,
                plat.toContentValues());

    }

    public long guardarBebida(bebida beb) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                bebidaContract.bebidaEntry.TABLE_NAME,
                null,
                beb.toContentValues());

    }

    public Cursor getLawyerById(String correo, String contraseña) {
        String columns[] = new String[]{usuarioContract.usuarioEntry.CORREO,usuarioContract.usuarioEntry.CONTRASEÑA};
        String columns2[] = new String[]{correo,contraseña};
        Cursor c = getReadableDatabase().query(
                usuarioContract.usuarioEntry.TABLE_NAME,
                columns,
                usuarioContract.usuarioEntry.CORREO + " LIKE ? AND "+usuarioContract.usuarioEntry.CONTRASEÑA + " LIKE ?",
                columns2,
                null,
                null,
                null);
        return c;
    }

    public Cursor obtenerUsuarioPorCorreo(String correo) {
        String columns[] = new String[]{usuarioContract.usuarioEntry.CORREO,usuarioContract.usuarioEntry.CONTRASEÑA};
        String columns2[] = new String[]{correo};
        Cursor c = getReadableDatabase().query(
                //a
                usuarioContract.usuarioEntry.TABLE_NAME,
                null,
                usuarioContract.usuarioEntry.CORREO + " LIKE ?",
                columns2,
                null,
                null,
                null);
        return c;
    }


    public Cursor obtenerTodasLasBebidas() {
        return getReadableDatabase()
                .query(
                        bebidaContract.bebidaEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor obtenerTodasLosPlatos() {
        return getReadableDatabase()
                .query(
                        platoContract.platoEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public boolean actualizarPerfil(String campo, String valor, String identificador){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "update " + usuarioContract.usuarioEntry.TABLE_NAME + " set " + campo + "='" + valor + "' where " + usuarioContract.usuarioEntry.CORREO +" like '" + identificador + "'";
        Object[] bindArgs = {};//identificador};
        try{
            db.execSQL(sql, bindArgs);
            return true;
        }catch(SQLException ex){
            return false;
        }
    }

}
