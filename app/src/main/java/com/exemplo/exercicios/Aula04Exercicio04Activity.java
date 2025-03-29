package com.exemplo.exercicios;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Aula04Exercicio04Activity extends AppCompatActivity {

    private Button btnUsuarios;
    private Button btnVendas;
    private Button btnRelatorios;
    private Button btnConfiguracoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula04_exercicio04);

        btnUsuarios = findViewById(R.id.btnUsuarios);
        btnVendas = findViewById(R.id.btnVendas);
        btnRelatorios = findViewById(R.id.btnRelatorios);
        btnConfiguracoes = findViewById(R.id.btnConfiguracoes);
        btnUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAction("Usuários");
            }
        });

        btnVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAction("Vendas");
            }
        });

        btnRelatorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAction("Relatórios");
            }
        });

        btnConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAction("Configurações");
            }
        });
    }

    private void performAction(String action) {
        Toast.makeText(this, "Abrindo: " + action, Toast.LENGTH_SHORT).show();
    }
}