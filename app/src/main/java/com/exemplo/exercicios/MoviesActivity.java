package com.exemplo.exercicios;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity {

    private EditText edtTitulo, edtDiretor, edtAno;
    private Spinner spnGenero;
    private CheckBox chkCinema;
    private RatingBar ratingNota;
    private ListView listViewMovies;
    private Button btnSave;
    private MoviesHelper databaseHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listMovies;
    private ArrayList<Integer> listIds;
    private int movieIdEditando = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtDiretor = findViewById(R.id.edtDiretor);
        edtAno = findViewById(R.id.edtAno);
        spnGenero = findViewById(R.id.spnGenero);
        chkCinema = findViewById(R.id.chkCinema);
        ratingNota = findViewById(R.id.ratingNota);
        btnSave = findViewById(R.id.btnSave);
        listViewMovies = findViewById(R.id.listViewMovies);
        databaseHelper = new MoviesHelper(this);

        ArrayAdapter<CharSequence> generoAdapter = ArrayAdapter.createFromResource(
                this, R.array.movie_genres, android.R.layout.simple_spinner_item);
        generoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGenero.setAdapter(generoAdapter);

        btnSave.setOnClickListener(v -> salvarOuAtualizar());

        listViewMovies.setOnItemClickListener((parent, view, position, id) -> carregarFilmeParaEdicao(position));

        listViewMovies.setOnItemLongClickListener((parent, view, position, id) -> {
            int movieId = listIds.get(position);
            int deletado = databaseHelper.deletarFilme(movieId);
            if (deletado > 0) {
                Toast.makeText(this, "Filme excluído!", Toast.LENGTH_SHORT).show();
                carregarFilmes();
            }
            return true;
        });

        carregarFilmes();
    }

    private void salvarOuAtualizar() {
        String titulo = edtTitulo.getText().toString();
        String diretor = edtDiretor.getText().toString();
        String anoStr = edtAno.getText().toString();
        int avaliacao = (int) ratingNota.getRating();
        String genero = spnGenero.getSelectedItem().toString();
        int cinema = chkCinema.isChecked() ? 1 : 0;

        if (titulo.isEmpty() || diretor.isEmpty() || anoStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        int ano = Integer.parseInt(anoStr);

        if (movieIdEditando == -1) {
            long resultado = databaseHelper.inserirFilme(titulo, diretor, ano, avaliacao, genero, cinema);
            if (resultado != -1) {
                Toast.makeText(this, "Filme cadastrado!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao salvar filme!", Toast.LENGTH_SHORT).show();
            }
        } else {
            int resultado = databaseHelper.atualizarFilme(movieIdEditando, titulo, diretor, ano, avaliacao, genero, cinema);
            if (resultado > 0) {
                Toast.makeText(this, "Filme atualizado!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao atualizar filme!", Toast.LENGTH_SHORT).show();
            }
            movieIdEditando = -1;
        }

        limparCampos();
        carregarFilmes();
    }

    private void carregarFilmeParaEdicao(int position) {
        int id = listIds.get(position);
        Cursor cursor = databaseHelper.getFilme(id);
        if (cursor != null && cursor.moveToFirst()) {
            edtTitulo.setText(cursor.getString(cursor.getColumnIndex("titulo")));
            edtDiretor.setText(cursor.getString(cursor.getColumnIndex("diretor")));
            edtAno.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("ano"))));
            ratingNota.setRating(cursor.getInt(cursor.getColumnIndex("avaliacao")));
            spnGenero.setSelection(((ArrayAdapter<String>) spnGenero.getAdapter())
                    .getPosition(cursor.getString(cursor.getColumnIndex("genero"))));
            chkCinema.setChecked(cursor.getInt(cursor.getColumnIndex("cinema")) == 1);
            movieIdEditando = id;
        }
    }

    private void carregarFilmes() {
        Cursor cursor = databaseHelper.listarTodosFilmes();
        listMovies = new ArrayList<>();
        listIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
            int ano = cursor.getInt(cursor.getColumnIndex("ano"));
            int nota = cursor.getInt(cursor.getColumnIndex("avaliacao"));
            String genero = cursor.getString(cursor.getColumnIndex("genero"));
            listMovies.add(titulo + " (" + ano + ") - " + genero + " - " + nota + "★");
            listIds.add(id);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMovies);
        listViewMovies.setAdapter(adapter);
    }

    private void limparCampos() {
        edtTitulo.setText("");
        edtDiretor.setText("");
        edtAno.setText("");
        ratingNota.setRating(0);
        spnGenero.setSelection(0);
        chkCinema.setChecked(false);
    }
}
