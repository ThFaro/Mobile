package com.exemplo.exercicios;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

public class IdadeVerificacaoActivity extends AppCompatActivity {

    private EditText editTextNome, editTextIdade;
    private TextView textViewResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idade_verificacao);

        editTextNome = findViewById(R.id.editTextNome);
        editTextIdade = findViewById(R.id.editTextIdade);
        textViewResultado = findViewById(R.id.textViewResultado);
        Button buttonVerificar = findViewById(R.id.buttonVerificar);

        buttonVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarIdade();
            }
        });
    }

    private void verificarIdade() {
        String nome = editTextNome.getText().toString();
        int idade = Integer.parseInt(editTextIdade.getText().toString());
        if (idade >= 18) {
            textViewResultado.setText(nome + " é maior de idade.");
        } else {
            textViewResultado.setText(nome + " não é maior de idade.");
        }
    }
}
