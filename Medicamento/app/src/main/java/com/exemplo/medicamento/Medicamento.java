package com.exemplo.medicamento;

public class Medicamento {
    public int id;
    public String nome;
    public String descricao;
    public String horario;
    public boolean tomado;

    public Medicamento(int id, String nome, String descricao, String horario, boolean tomado) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.horario = horario;
        this.tomado = tomado;
    }
}