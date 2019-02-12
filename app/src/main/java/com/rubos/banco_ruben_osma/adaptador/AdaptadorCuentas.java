package com.rubos.banco_ruben_osma.adaptador;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rubos.banco_ruben_osma.R;
import com.rubos.banco_ruben_osma.pojo.Cuenta;

import java.util.ArrayList;

public class AdaptadorCuentas extends ArrayAdapter<Cuenta> {

    Activity context;
    ArrayList<Cuenta> cuentas;

    public AdaptadorCuentas(Fragment context, ArrayList<Cuenta> cuentas) {
        super(context.getActivity(), R.layout.elemento_lista_cuentas, cuentas);
        this.context = context.getActivity();
        this.cuentas = cuentas;
    }

    public AdaptadorCuentas(Activity context, ArrayList<Cuenta> cuentas) {
        super(context, R.layout.elemento_lista_cuentas, cuentas);
        this.context = context;
        this.cuentas = cuentas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.elemento_lista_cuentas, null);

        TextView txtNumCuenta = item.findViewById(R.id.TxtNumCuenta);
        txtNumCuenta.setTextColor(Color.parseColor("#FFFFFF"));
        txtNumCuenta.setText(cuentas.get(position).getNumeroCuenta());

        return item;
    }
}