package com.exemplo.exercicios;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LetrasNomeActivity extends AppCompatActivity {

    private EditText editTextNome;
    private LinearLayout checkboxContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letras_nome);

        editTextNome = findViewById(R.id.editTextNome);
        Button buttonGerarCheckBoxes = findViewById(R.id.buttonGerarCheckBoxes);
        checkboxContainer = findViewById(R.id.checkboxContainer);

        buttonGerarCheckBoxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editTextNome.getText().toString().trim();
                checkboxContainer.removeAllViews();
                for (char letra : nome.toCharArray()) {
                    CheckBox checkBox = new CheckBox(LetrasNomeActivity.this);
                    checkBox.setText(String.valueOf(letra));
                    checkboxContainer.addView(checkBox);
                }
            }
        });
    }
}
