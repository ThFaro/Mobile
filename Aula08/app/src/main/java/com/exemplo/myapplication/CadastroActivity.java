package com.exemplo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtNomeExercicio, edtDuracaoExercicio;
    private Button btnSalvarExercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNomeExercicio = findViewById(R.id.edtNomeExercicio);
        edtDuracaoExercicio = findViewById(R.id.edtDuracaoExercicio);
        btnSalvarExercicio = findViewById(R.id.btnSalvarExercicio);

        btnSalvarExercicio.setOnClickListener(v -> {
            String nome = edtNomeExercicio.getText().toString();
            String duracaoStr = edtDuracaoExercicio.getText().toString();

            if (nome.isEmpty() || duracaoStr.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                int duracao = Integer.parseInt(duracaoStr);

                Intent intent = new Intent();
                intent.putExtra("nome", nome);
                intent.putExtra("duracao", duracao);
                setResult(RESULT_OK, intent);
                finish(); // Fecha a tela
            }
        });
    }
}
