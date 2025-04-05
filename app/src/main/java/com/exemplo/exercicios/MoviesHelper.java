package com.exemplo.exercicios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "filmes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "filmes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITULO = "titulo";
    private static final String COLUMN_DIRETOR = "diretor";
    private static final String COLUMN_ANO = "ano";
    private static final String COLUMN_AVALIACAO = "avaliacao";
    private static final String COLUMN_GENERO = "genero";
    private static final String COLUMN_CINEMA = "cinema";

    public MoviesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITULO + " TEXT,"
                + COLUMN_DIRETOR + " TEXT,"
                + COLUMN_ANO + " INTEGER,"
                + COLUMN_AVALIACAO + " INTEGER,"
                + COLUMN_GENERO + " TEXT,"
                + COLUMN_CINEMA + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long inserirFilme(String titulo, String diretor, int ano, int avaliacao, String genero, int cinema) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_DIRETOR, diretor);
        values.put(COLUMN_ANO, ano);
        values.put(COLUMN_AVALIACAO, avaliacao);
        values.put(COLUMN_GENERO, genero);
        values.put(COLUMN_CINEMA, cinema);
        return db.insert(TABLE_NAME, null, values);
    }

    public int atualizarFilme(int id, String titulo, String diretor, int ano, int avaliacao, String genero, int cinema) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_DIRETOR, diretor);
        values.put(COLUMN_ANO, ano);
        values.put(COLUMN_AVALIACAO, avaliacao);
        values.put(COLUMN_GENERO, genero);
        values.put(COLUMN_CINEMA, cinema);
        return db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public Cursor listarTodosFilmes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Cursor getFilme(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(id)});
    }

    public int deletarFilme(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
}
