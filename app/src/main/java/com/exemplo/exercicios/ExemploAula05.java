package com.exemplo.exercicios;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class ExemploAula05 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exemplo_aula05);

        final Button buttonOk = findViewById(R.id.botaoOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText campoEndereco = findViewById(R.id.campoEndereco);
                String endereco = campoEndereco.getText().toString();
                Uri uri = Uri.parse(endereco);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        final Button botaoTelefone = findViewById(R.id.botaoTelefone);
        botaoTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText campoTelefone = findViewById(R.id.campoTelefone);
                String telefone = campoTelefone.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telefone));
                startActivity(intent);
            }
        });

        final Button botaoCampoID = findViewById(R.id.botaoCampoID);
        botaoCampoID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText campoID = findViewById(R.id.campoID);
                String id = campoID.getText().toString();
                Uri uri = Uri.parse("content://com.android.contacts/contacts/" + id);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
