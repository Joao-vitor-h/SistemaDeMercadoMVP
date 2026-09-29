package ufes.br.sistemadesupermercado.mvp.model;

import java.util.Objects;

public class CategoriaModel {

    private int identificador;
    private String nome;
    private double percentualLucro;

    public CategoriaModel(int identificador, String nome, double percentualLucro) {
        Objects.requireNonNull(nome, "O nome é nulo.");
        if (nome.isBlank() || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome passado é inválido para a categoria.");
        }
        this.identificador = identificador;
        this.nome = nome;
        this.percentualLucro = percentualLucro;
    }

    public int getIdentificador() {
        return identificador;
    }

    public String getNome() {
        return nome;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public void setPercentualLucro(double percentualLucro) {
        this.percentualLucro = percentualLucro;
    }
}
