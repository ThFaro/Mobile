package com.exemplo.myapplication;

public class Exercicio {
    private String nome;
    private int duracao;

    public Exercicio(String nome, int duracao) {
        this.nome = nome;
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracao() {
        return duracao;
    }
}
