package com.rubos.banco_ruben_osma.fragmentos;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rubos.banco_ruben_osma.R;
import com.rubos.banco_ruben_osma.adaptador.AdaptadorCuentas;
import com.rubos.banco_ruben_osma.bd.MiBancoOperacional;
import com.rubos.banco_ruben_osma.pojo.Cliente;
import com.rubos.banco_ruben_osma.pojo.Cuenta;

import java.util.ArrayList;

public class Fragment_Cuentas extends Fragment implements AdapterView.OnItemClickListener {
    private Cliente cli;
    private MiBancoOperacional mbo;
    private ArrayList<Cuenta> arrayList = new ArrayList<>();
    private ListView lstCuentas;
    private CuentasListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__cuentas, container, false);
    }

    public void mostrarCuentas(Cliente cliente) {
        lstCuentas = getView().findViewById(R.id.LstCuentas);
        arrayList = cliente.getListaCuentas();
        lstCuentas.setAdapter(new AdaptadorCuentas(this, arrayList));
        lstCuentas.setOnItemClickListener(this);
    }

    public void setCuentasListener(CuentasListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listener != null) {
            listener.onCuentaSeleccionada((Cuenta) lstCuentas.getAdapter().getItem(position));
        }
    }
}
