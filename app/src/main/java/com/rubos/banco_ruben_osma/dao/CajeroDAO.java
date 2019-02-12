package com.rubos.banco_ruben_osma.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.rubos.banco_ruben_osma.bd.MiBD;
import java.util.ArrayList;
import static com.rubos.banco_ruben_osma.bd.Constantes.*;


public class CajeroDAO {

    private Context contexto;
    private MiBD dbHelper;
    private SQLiteDatabase db;


    public CajeroDAO(Context context) {
        this.contexto = context;
    }

    public CajeroDAO abrir() {
        dbHelper = MiBD.getInstance(contexto);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbHelper.close();
    }


    public Cursor getCursor() {
        Cursor c = db.query(true, CAJEROS_TABLE, CAMPOS_CAJEROS, null, null, null, null, null, null);
        return c;
    }


    public Cursor getRegistro(long id) {
        String condicion = FIELD_CAJEROS_ID + "=" + id;
        Cursor c = db.query(true, CAJEROS_TABLE, CAMPOS_CAJEROS, condicion, null, null, null, null, null);
        //Nos movemos al primer registro de la consulta
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public long insert(ContentValues reg) {
        if (db == null)
            abrir();
        return db.insert(CAJEROS_TABLE, null, reg);
    }


    public long update(ContentValues reg) {
        long result = 0;
        if (db == null)
            abrir();
        if (reg.containsKey(FIELD_CAJEROS_ID)) {
            // Obtenemos el id y lo borramos de los valores a actualizar, ya que el id no se actualizar
            long id = reg.getAsLong(FIELD_CAJEROS_ID);
            reg.remove(FIELD_CAJEROS_ID);
            // Actualizamos el registro con el identificador que hemos extraido

            result = db.update(CAJEROS_TABLE, reg, "_id=" + id, null);
        }
        return result;
    }

    public long delete(long id) {
        if (db == null)
            abrir();
        return db.delete(CAJEROS_TABLE, "_id=" + id, null);
    }

}
