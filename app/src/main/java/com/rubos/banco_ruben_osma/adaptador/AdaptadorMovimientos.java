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
import com.rubos.banco_ruben_osma.pojo.Movimiento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdaptadorMovimientos extends ArrayAdapter<Movimiento> {

    Activity context;
    ArrayList<Movimiento> movimientos;

    public AdaptadorMovimientos(Fragment context, ArrayList<Movimiento> movimientos) {
        super(context.getActivity(), R.layout.elemento_lista_movimientos, movimientos);
        this.context = context.getActivity();
        this.movimientos = movimientos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.elemento_lista_movimientos, null);

        TextView txtImporte = item.findViewById(R.id.TxtNumCuenta);
        Float importe = movimientos.get(position).getImporte();
        if (importe > 0) {
            txtImporte.setTextColor(Color.parseColor("#32e8aa"));
        } else {
            txtImporte.setTextColor(Color.parseColor("#FF0000"));
        }
        txtImporte.setText(Float.toString(importe) + "€");

        TextView txtFecha = item.findViewById(R.id.TxtFecha);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        txtFecha.setText(sdf.format(movimientos.get(position).getFechaOperacion()));

        TextView txtDescripcion = item.findViewById(R.id.TxtDescripcion);
        txtDescripcion.setText(movimientos.get(position).getDescripcion());

        return item;
    }
}