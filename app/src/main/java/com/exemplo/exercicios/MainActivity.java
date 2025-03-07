package com.exemplo.exercicios;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonVerificar).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, IdadeVerificacaoActivity.class)));
        findViewById(R.id.botaoAbrirCalculadora).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CalculadoraActivity.class)));
        findViewById(R.id.botaoAbrirCadastro).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CadastroUsuarioActivity.class)));
        findViewById(R.id.botaoAbrirLetrasNome).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LetrasNomeActivity.class)));
        findViewById(R.id.botaoAbrirPreferencias).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PreferenciasActivity.class)));
    }
}
