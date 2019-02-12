package com.rubos.banco_ruben_osma.fragmentos;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rubos.banco_ruben_osma.R;
import com.rubos.banco_ruben_osma.adaptador.AdaptadorMovimientos;
import com.rubos.banco_ruben_osma.bd.MiBancoOperacional;
import com.rubos.banco_ruben_osma.pojo.Cliente;
import com.rubos.banco_ruben_osma.pojo.Cuenta;
import com.rubos.banco_ruben_osma.pojo.Movimiento;

import java.util.ArrayList;


public class Fragment_Movimientos extends Fragment implements AdapterView.OnItemClickListener {
    private Cliente cli;
    private ListView lstMovimientos;
    private ArrayList<Movimiento> arrayMovimientos;
    private MiBancoOperacional mbo;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__movimientos, container, false);
    }

    public void mostrarMovimientos(Cuenta cuenta) {
        mbo = MiBancoOperacional.getInstance(getActivity());
        cuenta.setListaMovimientos(mbo.getMovimientos(cuenta));
        arrayMovimientos = cuenta.getListaMovimientos();
        lstMovimientos = getView().findViewById(R.id.LstMovimientos);
        lstMovimientos.setAdapter(new AdaptadorMovimientos(this, arrayMovimientos));
        lstMovimientos.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fragment = this.getActivity().getSupportFragmentManager();
        Dialogo_Movimientos dialog = newInstance((Movimiento)parent.getItemAtPosition(position));
        dialog.show(fragment, "tag");
    }

    static Dialogo_Movimientos newInstance(Movimiento m){
        Dialogo_Movimientos dialogo_movimientos = new Dialogo_Movimientos();
        Bundle bundle = new Bundle();
        bundle.putSerializable("MOVIMIENTO", m);
        dialogo_movimientos.setArguments(bundle);

        return dialogo_movimientos;
    }
}
