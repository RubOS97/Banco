package com.rubos.banco_ruben_osma.controlador;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.rubos.banco_ruben_osma.R;
import com.rubos.banco_ruben_osma.pojo.Cliente;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView usuario;
    Button posicion;
    Button ingresos;
    Button transferencias;
    Button promociones;
    Button cajeros;
    Button cambiarPass;
    Cliente cli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = getResources();

        usuario = findViewById(R.id.TxtSaludo);
        cli = (Cliente) this.getIntent().getSerializableExtra("CLIENTE");
        usuario.setText(res.getString(R.string.welcome_main) + ": " + cli.getNombre());

        posicion = findViewById(R.id.BtnPosicion);
        ingresos = findViewById(R.id.BtnIngresos);
        transferencias = findViewById(R.id.BtnTransferencias);
        promociones = findViewById(R.id.BtnPromociones);
        cajeros = findViewById(R.id.BtnCajeros);
        cambiarPass = findViewById(R.id.BtnCambiarPass);

        posicion.setOnClickListener(this);
        ingresos.setOnClickListener(this);
        transferencias.setOnClickListener(this);
        promociones.setOnClickListener(this);
        cajeros.setOnClickListener(this);
        cambiarPass.setOnClickListener(this);
        //setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_global:
                intent = new Intent(MainActivity.this, PosicionGlobal.class);
                intent.putExtra("CLIENTE", cli);
                startActivity(intent);
                break;

            case R.id.action_transferencias:
                intent = new Intent(MainActivity.this, Transferencias.class);
                intent.putExtra("CLIENTE", cli);
                startActivity(intent);
                break;

            case R.id.action_ingresos:
                intent = new Intent(MainActivity.this, Ingresos.class);
                startActivity(intent);
                break;

            case R.id.action_promociones:
                intent = new Intent(MainActivity.this, Promociones.class);
                startActivity(intent);
                break;

            case R.id.action_cambiarContraseña:
                intent = new Intent(MainActivity.this, CambiarPassword.class);
                intent.putExtra("CLIENTE", cli);
                startActivity(intent);
                break;

            case R.id.action_settings:
                intent = new Intent(MainActivity.this, Preferencias.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.BtnPosicion:
                intent = new Intent(MainActivity.this, PosicionGlobal.class);
                intent.putExtra("CLIENTE", cli);
                startActivity(intent);
                break;

            case R.id.BtnIngresos:
                intent = new Intent(MainActivity.this, Ingresos.class);
                startActivity(intent);
                break;

            case R.id.BtnTransferencias:
                intent = new Intent(MainActivity.this, Transferencias.class);
                intent.putExtra("CLIENTE", cli);
                startActivity(intent);
                break;

            case R.id.BtnPromociones:
                intent = new Intent(MainActivity.this, Promociones.class);
                startActivity(intent);
                break;

            case R.id.BtnCajeros:
                intent = new Intent(MainActivity.this, CajerosActivity.class);
                startActivity(intent);
                break;

            case R.id.BtnCambiarPass:
                intent = new Intent(MainActivity.this, CambiarPassword.class);
                intent.putExtra("CLIENTE", cli);
                startActivity(intent);
                break;
        }
    }
}
