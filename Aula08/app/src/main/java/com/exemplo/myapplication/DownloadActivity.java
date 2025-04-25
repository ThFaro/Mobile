package com.exemplo.myapplication;

import static java.lang.Compiler.enable;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DownloadActivity extends AppCompatActivity {

    private EditText edtTamanho;
    private Button btnDownload;
    private TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        edtTamanho = findViewById(R.id.edtTamanho);
        btnDownload = findViewById(R.id.btnDownload);
        txtResultado = findViewById(R.id.txtResultado);

        btnDownload.setOnClickListener(v -> {
            btnDownload.setEnabled(false);
            txtResultado.setText("Baixando...");
            

            new Thread(() -> {
                try {
                    Thread.sleep(3000); // Simula 3 segundos de download

                    runOnUiThread(() -> {
                        txtResultado.setText("Download finalizado!");
                        btnDownload.setEnabled(true);
                    });

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });
    }
}
