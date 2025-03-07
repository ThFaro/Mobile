package com.exemplo.exercicios;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class PreferenciasActivity extends AppCompatActivity {

    private CheckBox checkBoxNotificacoes, checkBoxModoEscuro, checkBoxLembrarLogin;
    private Button buttonSalvarPreferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        checkBoxNotificacoes = findViewById(R.id.checkBoxNotificacoes);
        checkBoxModoEscuro = findViewById(R.id.checkBoxModoEscuro);
        checkBoxLembrarLogin = findViewById(R.id.checkBoxLembrarLogin);
        buttonSalvarPreferencias = findViewById(R.id.buttonSalvarPreferencias);

        buttonSalvarPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                if (checkBoxNotificacoes.isChecked()) {
                    sb.append("Receber notificações, ");
                }
                if (checkBoxModoEscuro.isChecked()) {
                    sb.append("Modo escuro, ");
                }
                if (checkBoxLembrarLogin.isChecked()) {
                    sb.append("Lembrar login, ");
                }
                if (sb.length() > 0) {
                    sb.setLength(sb.length() - 2); // Remove the trailing comma and space
                    Toast.makeText(PreferenciasActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PreferenciasActivity.this, "Nenhuma preferência selecionada", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
