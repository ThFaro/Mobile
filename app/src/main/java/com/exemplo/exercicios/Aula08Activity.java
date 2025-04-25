package com.exemplo.exercicios;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Aula08Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        minhaThread t = new minhaThread();
        t.start();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aula08);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    class minhaThread extends Thread {
        public void run() {
            try {
                Thread.sleep(3000);
                TextView t = (TextView) findViewById(R.id.TextView1);
                t.setText("Atividade Iniciada");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
