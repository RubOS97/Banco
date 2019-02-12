package com.rubos.banco_ruben_osma.controlador;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.rubos.banco_ruben_osma.R;
import com.rubos.banco_ruben_osma.adaptador.AdaptadorCuentas;
import com.rubos.banco_ruben_osma.bd.MiBD;
import com.rubos.banco_ruben_osma.bd.MiBancoOperacional;
import com.rubos.banco_ruben_osma.pojo.Cliente;
import com.rubos.banco_ruben_osma.pojo.Cuenta;
import com.rubos.banco_ruben_osma.pojo.Movimiento;

import java.util.ArrayList;
import java.util.Calendar;

public class Transferencias extends AppCompatActivity implements AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener {
    private MiBD mb;
    Cliente cli;
    private MiBancoOperacional mbo;

    private GridView gridCuentas;
    private ArrayAdapter<String> adaptadorCuenta;
    private ArrayAdapter<String> adaptadorMonedas;
    private Spinner spinnerCuentaPropia, spinnerMoneda;
    private EditText textCuentaAjena, textImporte;
    private CheckBox checkJustificante;
    private RadioGroup radioCuentas;
    private RadioButton radioCuentaPropia, radioCuentaAjena;
    private ConstraintLayout.LayoutParams params1;
    private ConstraintLayout.LayoutParams params2;
    private ConstraintLayout constraintOculto;
    private Guideline guideLine1;
    private Guideline guideLine2;
    private String[] listaMonedas = new String[]{"€", "$", "£", "¥"};
    private String texto;
    private ArrayAdapter<Cuenta> adaptadorCuentas;
    private ArrayList<Cuenta> listaCuentas = new ArrayList<>();
    private Cuenta cuentaOrigen;
    private String[] stringCuentas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencias);

        mb = MiBD.getInstance(this);
        mbo = MiBancoOperacional.getInstance(this);

        cli = (Cliente) this.getIntent().getSerializableExtra("CLIENTE");
        listaCuentas = mbo.getCuentas(cli);
        stringCuentas = new String[listaCuentas.size()];
        for (int i = 0; i < stringCuentas.length; i++) {
            stringCuentas[i] = listaCuentas.get(i).getNumeroCuenta();
        }


        gridCuentas = findViewById(R.id.GridCuentas);
        radioCuentas = findViewById(R.id.RadioCuentas);
        radioCuentaPropia = findViewById(R.id.RadioPropia);
        radioCuentaAjena = findViewById(R.id.RadioAjena);
        guideLine1 = findViewById(R.id.GuideLine1);
        guideLine2 = findViewById(R.id.GuideLine2);
        constraintOculto = findViewById(R.id.ConstraintOculto);
        constraintOculto.setVisibility(View.INVISIBLE);
        spinnerCuentaPropia = findViewById(R.id.SpinnerCuenta);
        spinnerMoneda = findViewById(R.id.SpinnerMoneda);
        checkJustificante = findViewById(R.id.CheckJustificante);
        textImporte = findViewById(R.id.TxtNumCuenta);
        textCuentaAjena = findViewById(R.id.TextCuentaAjena);

        params1 = (ConstraintLayout.LayoutParams) guideLine1.getLayoutParams();
        params2 = (ConstraintLayout.LayoutParams) guideLine2.getLayoutParams();
        params1.guidePercent = 0.38f;
        params2.guidePercent = 0.38f;

        spinnerCuentaPropia.setAdapter(adaptadorCuenta);
        spinnerMoneda.setAdapter(adaptadorMonedas);
        adaptadorMonedas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaMonedas);
        adaptadorCuenta = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringCuentas);
        spinnerCuentaPropia.setAdapter(adaptadorCuenta);
        spinnerMoneda.setAdapter(adaptadorMonedas);
        radioCuentas.setOnCheckedChangeListener(this);

        adaptadorCuentas = new AdaptadorCuentas(this, listaCuentas);
        gridCuentas.setAdapter(adaptadorCuentas);
        gridCuentas.setOnItemClickListener(this);
    }

    public void enviar(View v) {
        Movimiento m = new Movimiento();
        m.setCuentaOrigen(cuentaOrigen);
        if (radioCuentaPropia.isChecked()) {
            String stringDestino = spinnerCuentaPropia.getSelectedItem().toString();
            Cuenta cuentaDestino = null;
            for (int i = 0; i < stringCuentas.length; i++) {
                if (listaCuentas.get(i).getNumeroCuenta() == stringDestino){
                    cuentaDestino = listaCuentas.get(i);
                }
            }
            m.setCuentaDestino(cuentaDestino);
        } else if (radioCuentaAjena.isChecked()) {
            m.setCuentaDestino((Cuenta) textCuentaAjena.getText());
            texto += "cuenta Destino: " + textCuentaAjena.getText().toString() + "\n";
        }

        if (textImporte.getText().length() != 0) {
            m.setImporte((Float.parseFloat(textImporte.getText().toString()) * -1));
            Log.e("TEST", Float.toString(m.getImporte()));
        }
        m.setTipo(0);
        if (checkJustificante.isChecked()) {
            texto += "Si Enviar Justificante \n";
        } else {
            texto += "No Enviar Justificante \n";
        }
        transferencia(m);
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
        texto = "";
    }

    public void cancelar(View v) {
        radioCuentas.clearCheck();
        params1 = (ConstraintLayout.LayoutParams) guideLine1.getLayoutParams();
        params2 = (ConstraintLayout.LayoutParams) guideLine2.getLayoutParams();
        params1.guidePercent = 0.38f;
        params2.guidePercent = 0.38f;
        guideLine1.setLayoutParams(params1);
        guideLine2.setLayoutParams(params2);
        constraintOculto.setVisibility(View.INVISIBLE);
        textImporte.setText("");
        textCuentaAjena.setText("");
        checkJustificante.setChecked(false);
        texto = "";
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Has selecionado: " + adaptadorCuentas.getItem(position).getNumeroCuenta(), Toast.LENGTH_LONG).show();
        texto = "Cuenta Origen: " + adaptadorCuentas.getItem(position).getNumeroCuenta() + "\n";
        cuentaOrigen = adaptadorCuentas.getItem(position);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.RadioPropia) {
            constraintOculto.setVisibility(View.VISIBLE);
            params1.guidePercent = 0.35f;
            params2.guidePercent = 0.45f;
            guideLine1.setLayoutParams(params1);
            guideLine2.setLayoutParams(params2);
            textCuentaAjena.setVisibility(View.INVISIBLE);
            spinnerCuentaPropia.setVisibility(View.VISIBLE);
        } else if (checkedId == R.id.RadioAjena) {
            constraintOculto.setVisibility(View.VISIBLE);
            params1.guidePercent = 0.40f;
            params2.guidePercent = 0.50f;
            guideLine1.setLayoutParams(params1);
            guideLine2.setLayoutParams(params2);
            textCuentaAjena.setVisibility(View.VISIBLE);
            spinnerCuentaPropia.setVisibility(View.INVISIBLE);
        }
    }

    public int transferencia(Movimiento m) {

        Cuenta cDestino = m.getCuentaDestino();
        Cuenta cOrigen = m.getCuentaOrigen();
        int tipo = m.getTipo();

        if (!mb.existeCuenta(cDestino.getBanco(), cDestino.getSucursal(), cDestino.getDc(), cDestino.getNumeroCuenta())) {
            return 1;
        }

        if (!mb.existeCuenta(cOrigen.getBanco(), cDestino.getSucursal(), cDestino.getDc(), cDestino.getNumeroCuenta())) {
            return 1;
        }

        if (m.getImporte() > cDestino.getSaldoActual()) {
            return 2;
        }

        m.setFechaOperacion(Calendar.getInstance().getTime());

        if (tipo == 0) {
            mb.insercionMovimiento(m);
            m.getCuentaOrigen().setSaldoActual(m.getCuentaOrigen().getSaldoActual()-m.getImporte());
            m.getCuentaDestino().setSaldoActual(m.getCuentaDestino().getSaldoActual()+m.getImporte());
            mb.actualizarSaldo(cOrigen);
            mb.actualizarSaldo(cDestino);
            return 0;
        }

        return 3;
    }
}