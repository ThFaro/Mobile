package com.mobile.tarefadiariaapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.tarefadiariaapplication.R;
import com.mobile.tarefadiariaapplication.models.Tarefa;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {

    private List<Tarefa> lista;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
    private OnCheckboxClickListener checkboxClickListener;

    public TarefaAdapter(List<Tarefa> lista) {
        this.lista = lista;
    }

    public interface OnItemClickListener {
        void onItemClick(Tarefa tarefa);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Tarefa tarefa);
    }

    public interface OnCheckboxClickListener {
        void onCheckboxClick(Tarefa tarefa);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    public void setOnCheckboxClickListener(OnCheckboxClickListener listener) {
        this.checkboxClickListener = listener;
    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarefa, parent, false);
        return new TarefaViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder holder, int position) {
        Tarefa tarefa = lista.get(position);
        holder.txtTitulo.setText(tarefa.getTitulo());
        holder.checkBox.setChecked(tarefa.isConcluida());

        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) itemClickListener.onItemClick(tarefa);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (itemLongClickListener != null) itemLongClickListener.onItemLongClick(tarefa);
            return true;
        });

        holder.checkBox.setOnClickListener(v -> {
            tarefa.setConcluida(holder.checkBox.isChecked()); // ✅ Atualiza localmente o valor
            if (checkboxClickListener != null) checkboxClickListener.onCheckboxClick(tarefa);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class TarefaViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo;
        CheckBox checkBox;

        public TarefaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloTarefa);
            checkBox = itemView.findViewById(R.id.checkConcluida);
        }
    }
}
