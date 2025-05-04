package com.exemplo.medicamento;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class MedicamentoAdapter extends ArrayAdapter<Medicamento> {
    public MedicamentoAdapter(Context context, List<Medicamento> lista) {
        super(context, 0, lista);
    }

    @NonNull
    @Override
    public View getView(int pos, View convertView, @NonNull ViewGroup parent) {
        Medicamento m = getItem(pos);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);
        text1.setText(m.nome);
        text2.setText(m.horario);
        text1.setPaintFlags(m.tomado ? text1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : text1.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        return convertView;
    }
}