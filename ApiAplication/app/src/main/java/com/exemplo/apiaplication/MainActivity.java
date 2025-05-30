package com.exemplo.apiaplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText editTextCep = findViewById(R.id.editCEP);
        editTextCep.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                new Thread(() -> {
                    try {
                        String cep = editTextCep.getText().toString();
                        URL url = new URL("https://viacep.com.br/ws/" + cep + "/json");
                        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                        if (conexao.getResponseCode() != 200)
                            throw new RuntimeException("HTTP error code: " + conexao.getResponseCode());

                        BufferedReader resposta = new BufferedReader(
                                new InputStreamReader(conexao.getInputStream()));
                        StringBuilder json = new StringBuilder();
                        String linha;

                        while ((linha = resposta.readLine()) != null) {
                            json.append(linha);
                        }

                        String jsonString = json.toString();

                        runOnUiThread(() -> {
                            try {
                                TextView textView = findViewById(R.id.txvEndereco);
                                textView.setText(jsonString);

                                Gson gson = new Gson();
                                Endereco endereco = gson.fromJson(jsonString, Endereco.class);

                                EditText editUf = findViewById(R.id.editTextText4);
                                editUf.setText(endereco.getUf());
                            } catch (Exception e) {
                                Log.e("JSON", "Erro ao processar JSON: " + e.getMessage());
                            }
                        });

                    } catch (Exception e) {
                        Log.e("JSON", "Erro na requisição: " + e.getMessage());
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }
}
