package com.exemplo.exercicios;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CheckBox;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText editTextNome, editTextIdade, editTextUF, editTextCidade, editTextTelefone, editTextEmail;
    private RadioGroup radioGroupTamanho;
    private RadioButton radioButtonP, radioButtonM, radioButtonG;
    private CheckBox checkBoxPreto, checkBoxBranco, checkBoxVermelho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        editTextNome = findViewById(R.id.editTextNome);
        editTextIdade = findViewById(R.id.editTextIdade);
        editTextUF = findViewById(R.id.editTextUF);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroupTamanho = findViewById(R.id.radioGroupTamanho);
        radioButtonP = findViewById(R.id.radioButtonP);
        radioButtonM = findViewById(R.id.radioButtonM);
        radioButtonG = findViewById(R.id.radioButtonG);
        checkBoxPreto = findViewById(R.id.checkBoxPreto);
        checkBoxBranco = findViewById(R.id.checkBoxBranco);
        checkBoxVermelho = findViewById(R.id.checkBoxVermelho);

    }
}
