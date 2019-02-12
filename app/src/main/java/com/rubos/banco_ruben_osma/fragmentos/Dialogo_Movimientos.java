package com.rubos.banco_ruben_osma.fragmentos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rubos.banco_ruben_osma.R;
import com.rubos.banco_ruben_osma.pojo.Movimiento;

import java.text.SimpleDateFormat;

public class Dialogo_Movimientos extends DialogFragment implements View.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Movimiento m = (Movimiento) this.getArguments().getSerializable("MOVIMIENTO");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_movimientos, null);

        TextView txtFecha = view.findViewById(R.id.TxtFecha);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        txtFecha.setText(sdf.format(m.getFechaOperacion()));


        TextView txtImporte = view.findViewById(R.id.TxtImporte);
        Float importe = m.getImporte();

        if (importe > 0) {
            txtImporte.setTextColor(Color.parseColor("#32e8aa"));
        } else {
            txtImporte.setTextColor(Color.parseColor("#FF0000"));
        }
        txtImporte.setText(Float.toString(importe) + "€");

        TextView txtDescripcion = view.findViewById(R.id.TxtDescripcion);
        txtDescripcion.setText(m.getDescripcion());

        TextView txtCuentaOrigen = view.findViewById(R.id.TxtCuentaOrigen);
        txtCuentaOrigen.setText(m.getCuentaOrigen().getNumeroCuenta());

        TextView txtCuentaDestino = view.findViewById(R.id.TxtCuentaDestino);
        txtCuentaDestino.setText(m.getCuentaDestino().getNumeroCuenta());

        Button btnAceptar = view.findViewById(R.id.BtnAceptar);
        btnAceptar.setOnClickListener(this);

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        getDialog().cancel();
    }
}
