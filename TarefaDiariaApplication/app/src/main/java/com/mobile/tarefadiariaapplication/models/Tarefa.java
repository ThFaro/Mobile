package com.mobile.tarefadiariaapplication.models;

public class Tarefa {
    private String id;
    private String titulo;
    private boolean concluida;

    public Tarefa() {}

    public Tarefa(String id, String titulo, boolean concluida) {
        this.id = id;
        this.titulo = titulo;
        this.concluida = concluida;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
}
