package com.rubos.banco_ruben_osma.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rubos.banco_ruben_osma.R;
import com.rubos.banco_ruben_osma.bd.MiBancoOperacional;
import com.rubos.banco_ruben_osma.pojo.Cliente;

public class CambiarPassword extends AppCompatActivity implements View.OnClickListener {

    Cliente cli;
    EditText txtPass1;
    EditText txtPass2;
    EditText txtPass3;
    Button btnAceptar;
    MiBancoOperacional mbo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);

        cli = (Cliente) this.getIntent().getSerializableExtra("CLIENTE");
        mbo = MiBancoOperacional.getInstance(this);

        txtPass1 = findViewById(R.id.Password1);
        txtPass2 = findViewById(R.id.Password2);
        txtPass3 = findViewById(R.id.Password3);

        btnAceptar = findViewById(R.id.BtnAceptar);
        btnAceptar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (txtPass1.getText().length() == 0 || txtPass2.getText().length() == 0 || txtPass3.getText().length() == 0) {
            Toast.makeText(this, "Ningún campo puede estar vacio", Toast.LENGTH_LONG).show();
        } else {
            if (!txtPass1.getText().toString().equals(cli.getClaveSeguridad())) {
                Toast.makeText(this, "Error, la contraseña no es correcta", Toast.LENGTH_LONG).show();
            } else {
                if (!txtPass2.getText().toString().equals(txtPass3.getText().toString())) {
                    Toast.makeText(this, "Error, las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                } else {
                    cli.setClaveSeguridad(txtPass2.getText().toString());
                    mbo.changePassword(cli);
                    Toast.makeText(this, "Contraseña cambiada", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CambiarPassword.this, MainActivity.class);
                    intent.putExtra("CLIENTE", cli);
                    startActivity(intent);
                }
            }
        }
    }
}
