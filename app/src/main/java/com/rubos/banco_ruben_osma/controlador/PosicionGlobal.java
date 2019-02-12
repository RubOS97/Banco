package com.rubos.banco_ruben_osma.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.rubos.banco_ruben_osma.R;
import com.rubos.banco_ruben_osma.bd.MiBancoOperacional;
import com.rubos.banco_ruben_osma.fragmentos.CuentasListener;
import com.rubos.banco_ruben_osma.fragmentos.Fragment_Cuentas;
import com.rubos.banco_ruben_osma.fragmentos.Fragment_Movimientos;
import com.rubos.banco_ruben_osma.pojo.Cliente;
import com.rubos.banco_ruben_osma.pojo.Cuenta;

public class PosicionGlobal extends AppCompatActivity implements CuentasListener {
    private Cliente cli;
    private ArrayAdapter<String> adaptadorCuentas;
    private Fragment_Cuentas frgCuentas;
    private MiBancoOperacional mbo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posicion_global);
        mbo = MiBancoOperacional.getInstance(this);

        cli = (Cliente) this.getIntent().getSerializableExtra("CLIENTE");
        cli.setListaCuentas(mbo.getCuentas(cli));

        frgCuentas = (Fragment_Cuentas) getSupportFragmentManager().findFragmentById(R.id.FrgCuentas);
        frgCuentas.mostrarCuentas(cli);
        frgCuentas.setCuentasListener(this);
    }

    @Override
    public void onCuentaSeleccionada(Cuenta cuenta) {
        // Si es diferente a null, existe el fragment en la activity (TRUE)
        boolean hayMovimientos = (getSupportFragmentManager().findFragmentById(R.id.FrgMovimientos)) != null;
        if (hayMovimientos) {
            ((Fragment_Movimientos) getSupportFragmentManager().findFragmentById(R.id.FrgMovimientos)).mostrarMovimientos(cuenta);
        } else {
            Intent intent = new Intent(this, PosicionCuenta.class);
            intent.putExtra("CUENTA", cuenta);
            startActivity(intent);
        }
    }
}