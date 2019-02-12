package com.rubos.banco_ruben_osma.controlador;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rubos.banco_ruben_osma.R;
import com.rubos.banco_ruben_osma.adaptador.CajerosCursorAdapter;
import com.rubos.banco_ruben_osma.bd.Constantes;
import com.rubos.banco_ruben_osma.dao.CajeroDAO;

public class CajerosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lista;
    CajeroDAO cajeroDAO;
    private Cursor cursor;
    private CajerosCursorAdapter cajeroAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cajeros);

        lista = (ListView) findViewById(R.id.lista);
        lista.setOnItemClickListener(this);
        cajeroDAO = new CajeroDAO(this);

        try {

            cajeroDAO.abrir();
            cursor = cajeroDAO.getCursor();
            startManagingCursor(cursor);
            cajeroAdapter = new CajerosCursorAdapter(this, cursor);
            lista.setAdapter(cajeroAdapter);


        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Se ha producido un error al abrir la base de datos.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cajeros_crear, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, GestionCajerosActivity.class);
        i.putExtra(Constantes.C_MODO, Constantes.C_VISUALIZAR);
        i.putExtra(Constantes.FIELD_CAJEROS_ID, id);
        startActivityForResult(i, Constantes.C_VISUALIZAR);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;
        switch (item.getItemId()) {
            case R.id.menu_crear:
                i = new Intent(this, GestionCajerosActivity.class);
                i.putExtra(Constantes.C_MODO, Constantes.C_CREAR);
                startActivityForResult(i, Constantes.C_CREAR);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode) {
            case Constantes.C_CREAR:
                if (resultCode == RESULT_OK)
                    recargar_lista();
            case Constantes.C_VISUALIZAR:
                if (resultCode == RESULT_OK)
                    recargar_lista();
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void recargar_lista() {
        CajeroDAO cajeroDAO = new CajeroDAO(getBaseContext());
        cajeroDAO.abrir();
        CajerosCursorAdapter hipotecasCursorAdapter = new CajerosCursorAdapter(this, cajeroDAO.getCursor());
        lista.setAdapter(hipotecasCursorAdapter);
    }



}
