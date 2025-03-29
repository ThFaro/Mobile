package com.exemplo.exercicios;

import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Aula06ExercicioActivity extends AppCompatActivity {

    private EditText edtTitulo, edtDescricao;
    private ListView listViewTarefas;
    private Button btnSalvar, btnAtualizar;
    private CheckBox chkConcluida;
    private TaskHelper databaseHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listaTarefas;
    private ArrayList<Integer> listaIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula06_exercicio);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescricao = findViewById(R.id.edtDescricao);
        chkConcluida = findViewById(R.id.chkStatus);
        btnSalvar = findViewById(R.id.btnSalvarTarefa);
        btnAtualizar = findViewById(R.id.btnAtualizarTarefa);
        listViewTarefas = findViewById(R.id.listViewTarefas);
        databaseHelper = new TaskHelper(this);

        btnSalvar.setOnClickListener(v -> {
            String titulo = edtTitulo.getText().toString();
            String descricao = edtDescricao.getText().toString();
            int status = chkConcluida.isChecked() ? 1 : 0;
            if (!titulo.isEmpty() && !descricao.isEmpty()) {
                long resultado = databaseHelper.inserirTarefa(titulo, descricao, status);
                if (resultado != -1) {
                    Toast.makeText(this, "Tarefa salva!", Toast.LENGTH_SHORT).show();
                    edtTitulo.setText("");
                    edtDescricao.setText("");
                    chkConcluida.setChecked(false);
                    carregarTarefas();
                } else {
                    Toast.makeText(this, "Erro ao salvar tarefa!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });

        listViewTarefas.setOnItemClickListener((parent, view, position, id) -> {
            int taskId = listaIds.get(position);
            String titulo = listaTarefas.get(position).split(" - ")[1];
            String descricao = listaTarefas.get(position).split(" - ")[2];
            int status = Integer.parseInt(listaTarefas.get(position).split(" - ")[3]);

            edtTitulo.setText(titulo);
            edtDescricao.setText(descricao);
            chkConcluida.setChecked(status == 1);

            btnSalvar.setVisibility(View.GONE);
            btnAtualizar.setVisibility(View.VISIBLE);

            btnAtualizar.setOnClickListener(v -> atualizarTarefa(taskId));
        });

        listViewTarefas.setOnItemLongClickListener((adapterView, view1, pos, l) -> {
            int idUsuario = listaIds.get(pos);
            int deletado = databaseHelper.excluirTarefa(idUsuario);
            if (deletado > 0) {
                Toast.makeText(this, "Tarefa excluída!", Toast.LENGTH_SHORT).show();
                carregarTarefas();
            }
            return true;
        });

        carregarTarefas();
    }

    private void atualizarTarefa(int taskId) {
        String novoTitulo = edtTitulo.getText().toString();
        String novaDescricao = edtDescricao.getText().toString();
        int novoStatus = chkConcluida.isChecked() ? 1 : 0;
        if (!novoTitulo.isEmpty() && !novaDescricao.isEmpty()) {
            int resultado = databaseHelper.atualizarTarefa(taskId, novoTitulo, novaDescricao, novoStatus);
            if (resultado > 0) {
                Toast.makeText(this, "Tarefa atualizada!", Toast.LENGTH_SHORT).show();
                carregarTarefas();
                edtTitulo.setText("");
                edtDescricao.setText("");
                chkConcluida.setChecked(false);
                btnSalvar.setVisibility(View.VISIBLE);
                btnAtualizar.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, "Erro ao atualizar tarefa!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void carregarTarefas() {
        Cursor cursor = databaseHelper.listarTarefas();
        listaTarefas = new ArrayList<>();
        listaIds = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String titulo = cursor.getString(1);
                String descricao = cursor.getString(2);
                int status = cursor.getInt(3);
                listaTarefas.add(id + " - " + titulo + " - " + descricao + " - " + status);
                listaIds.add(id);
            } while (cursor.moveToNext());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaTarefas);
        listViewTarefas.setAdapter(adapter);
    }
}
