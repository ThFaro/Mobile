package com.exemplo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Ac2Activity extends AppCompatActivity {

    private ArrayList<Exercicio> listaExercicios = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private TextView txtAtual, txtContador;
    private Button btnCadastrar, btnIniciar;
    private Handler handler = new Handler();
    private int indexAtual = 0;
    private Thread treinoThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac2);

        listView = findViewById(R.id.listViewExercicios);
        txtAtual = findViewById(R.id.txtExercicioAtual);
        txtContador = findViewById(R.id.txtContador);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnIniciar = findViewById(R.id.btnIniciarTreino);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(adapter);

        btnCadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(Ac2Activity.this, CadastroActivity.class);
            startActivityForResult(intent, 1);
        });

        btnIniciar.setOnClickListener(v -> iniciarTreino());
    }

    private void iniciarTreino() {
        if (listaExercicios.isEmpty()) {
            txtAtual.setText("Nenhum exercício cadastrado");
            return;
        }

        indexAtual = 0;
        treinoThread = new Thread(() -> {
            for (Exercicio exercicio : listaExercicios) {
                runOnUiThread(() -> txtAtual.setText(exercicio.getNome()));
                int segundos = exercicio.getDuracao();

                while (segundos >= 0) {
                    int finalSegundos = segundos;
                    runOnUiThread(() -> txtContador.setText(finalSegundos + "s"));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    segundos--;
                }
                indexAtual++;
            }
            runOnUiThread(() -> {
                txtAtual.setText("Treino concluído!");
                txtContador.setText("");
            });
        });
        treinoThread.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String nome = data.getStringExtra("nome");
            int duracao = data.getIntExtra("duracao", 30);
            listaExercicios.add(new Exercicio(nome, duracao));
            atualizarLista();
        }
    }

    private void atualizarLista() {
        ArrayList<String> nomes = new ArrayList<>();
        for (Exercicio e : listaExercicios) {
            nomes.add(e.getNome() + " - " + e.getDuracao() + "s");
        }
        adapter.clear();
        adapter.addAll(nomes);
    }
}
