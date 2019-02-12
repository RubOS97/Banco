package com.rubos.banco_ruben_osma.controlador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
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

import java.util.Locale;

public class Login extends AppCompatActivity {
    MiBancoOperacional mbo;
    Button btn_login, btn_salir;
    EditText dni, password;
    Cliente cli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mbo = MiBancoOperacional.getInstance(this);

        dni = findViewById(R.id.Dni);
        password = findViewById(R.id.Password);


        SharedPreferences pref = this.getSharedPreferences("preferenciasBancarias", Context.MODE_PRIVATE);
        String idioma = pref.getString("pais", "ESP");

        if (idioma.contains("ESP")){
            Locale l = new Locale("es", "ES");
            Locale.setDefault(l);
            Configuration config = new Configuration();
            config.locale = l;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }else {
            Locale l = new Locale("en", "EN");
            Locale.setDefault(l);
            Configuration config = new Configuration();
            config.locale = l;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }



    }

    public void iniciarSesion(View v) {
        cli = new Cliente();
        cli.setNif(dni.getText().toString());
        cli.setClaveSeguridad(password.getText().toString());

        cli = mbo.login(cli);

        if (cli == null) {
            Toast.makeText(this, "Error, DNI o Contraseña incorrectos.", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra("CLIENTE", cli);
            startActivity(intent);
        }
    }

    public void salir(View v) {
        Intent intent = new Intent(Login.this, Welcome.class);
        startActivity(intent);
    }

}
