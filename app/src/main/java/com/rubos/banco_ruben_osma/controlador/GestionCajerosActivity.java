package com.rubos.banco_ruben_osma.controlador;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.rubos.banco_ruben_osma.R;
import com.rubos.banco_ruben_osma.dao.CajeroDAO;

import static com.rubos.banco_ruben_osma.bd.Constantes.*;

public class GestionCajerosActivity extends AppCompatActivity {

    private CajeroDAO cajeroDAO;
    private Cursor cursor;
    private int modo;
    private long id;
    private EditText direcion;
    private EditText longitud;
    private EditText latitud;
    private EditText zoom;
    private Button boton_guardar;
    private Button boton_cancelar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_cajeros);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra == null) return;

        direcion=findViewById(R.id.edit_direccion);
        longitud=findViewById(R.id.longitud);
        latitud=findViewById(R.id.latitud);
        zoom=findViewById(R.id.zoom);
        boton_guardar=findViewById(R.id.boton_guardar);
        boton_cancelar=findViewById(R.id.boton_cancelar);

        cajeroDAO = new CajeroDAO(this);
        cajeroDAO.abrir();

        if (extra.containsKey(FIELD_CAJEROS_ID)) {
            id = extra.getLong(FIELD_CAJEROS_ID);
            consultar(id);
        }

        establecerModo(extra.getInt(C_MODO));

        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        boton_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }




    private void establecerModo(int m) {
        this.modo = m;
        if (modo == C_VISUALIZAR) {
            this.setEdicion(false);
        } else if (modo == C_CREAR) {
            this.setEdicion(true);
        } else if (modo == C_EDITAR) {
            this.setEdicion(true);
        }
    }
    private void consultar(long id) {

        cursor = cajeroDAO.getRegistro(id);
        direcion.setText(cursor.getString(
                cursor.getColumnIndex(FIELD_DIRECCION)));
        latitud.setText(cursor.getString(
                cursor.getColumnIndex(FIELD_LAT)));
        longitud.setText(cursor.getString(
                cursor.getColumnIndex(FIELD_LNG)));
        zoom.setText(cursor.getString(
                cursor.getColumnIndex(FIELD_ZOOM)));

    }

    private void setEdicion(boolean opcion) {
        direcion.setEnabled(opcion);
        latitud.setEnabled(opcion);
        longitud.setEnabled(opcion);
        zoom.setEnabled(opcion);
    }



    private void guardar() {
        ContentValues reg = new ContentValues();

        if (modo == C_EDITAR) {
            reg.put(FIELD_CAJEROS_ID, id);
        }
        reg.put(FIELD_DIRECCION, direcion.getText().toString());
        reg.put(FIELD_LAT, latitud.getText().toString());
        reg.put(FIELD_LNG, longitud.getText().toString());
        reg.put(FIELD_ZOOM, zoom.getText().toString());

        if (modo == C_CREAR) {
            cajeroDAO.insert(reg);
        } else if (modo == C_EDITAR) {
            cajeroDAO.update(reg);
        }
        setResult(RESULT_OK);
        finish();
    }

    private void cancelar() {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        if (modo == C_VISUALIZAR)
            getMenuInflater().inflate(R.menu.menu_cajeros_ver, menu);
        else
            getMenuInflater().inflate(R.menu.menu_cajeros_editar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_editar:
                establecerModo(C_EDITAR);
                return true;
            case R.id.menu_eliminar:
                borrar(id);
                return true;
            case R.id.menu_cancelar:
                cancelar();
                return true;
            case R.id.menu_guardar:
                guardar();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void borrar(final long id) {
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);
        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle("Borrar");
        dialogEliminar.setMessage("Estats seguro que quieres borrar");
        dialogEliminar.setCancelable(false);
        dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        cajeroDAO.delete(id);
                        // Devolvemos el control
                        setResult(RESULT_OK);
                        finish();
                    }
                });
        dialogEliminar.setNegativeButton(android.R.string.no, null);
        dialogEliminar.show();
    }
}
