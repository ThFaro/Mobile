package com.exemplo.exercicios;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculadoraActivity extends AppCompatActivity {

    EditText editNumeroUm, editNumeroDois;
    TextView textoResultado;
    Button botaoSoma, botaoSubtracao, botaoMultiplicacao, botaoDivisao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        editNumeroUm = findViewById(R.id.editNumeroUm);
        editNumeroDois = findViewById(R.id.editNumeroDois);
        textoResultado = findViewById(R.id.textoResultado);
        botaoSoma = findViewById(R.id.botaoSoma);
        botaoSubtracao = findViewById(R.id.botaoSubtracao);
        botaoMultiplicacao = findViewById(R.id.botaoMultiplicacao);
        botaoDivisao = findViewById(R.id.botaoDivisao);

        botaoSoma.setOnClickListener(v -> performOperation('+'));
        botaoSubtracao.setOnClickListener(v -> performOperation('-'));
        botaoMultiplicacao.setOnClickListener(v -> performOperation('*'));
        botaoDivisao.setOnClickListener(v -> performOperation('/'));

    }

    private void performOperation(char operation) {
        double n1 = Double.parseDouble(editNumeroUm.getText().toString());
        double n2 = Double.parseDouble(editNumeroDois.getText().toString());
        double result;

        switch (operation) {
            case '+':
                result = n1 + n2;
                break;
            case '-':
                result = n1 - n2;
                break;
            case '*':
                result = n1 * n2;
                break;
            case '/':
                if (n2 != 0) {
                    result = n1 / n2;
                } else {
                    textoResultado.setText("Erro: divisão por zero!");
                    return;
                }
                break;
            default:
                result = 0;
                break;
        }
        textoResultado.setText(String.format("Resultado: %.2f", result));
    }


}
