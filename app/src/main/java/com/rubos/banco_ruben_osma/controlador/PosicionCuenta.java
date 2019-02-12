package com.rubos.banco_ruben_osma.controlador;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rubos.banco_ruben_osma.R;
import com.rubos.banco_ruben_osma.bd.MiBancoOperacional;
import com.rubos.banco_ruben_osma.fragmentos.Fragment_Movimientos;
import com.rubos.banco_ruben_osma.pojo.Cuenta;

public class PosicionCuenta extends AppCompatActivity {

    private MiBancoOperacional mbo;
    private Cuenta cuenta;
    private Fragment_Movimientos frgMovimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posicion_cuenta);
        mbo = MiBancoOperacional.getInstance(this);

        cuenta = (Cuenta) this.getIntent().getSerializableExtra("CUENTA");

        frgMovimientos = (Fragment_Movimientos) getSupportFragmentManager().findFragmentById(R.id.FrgMovimientos);
        frgMovimientos.mostrarMovimientos(cuenta);
    }
}