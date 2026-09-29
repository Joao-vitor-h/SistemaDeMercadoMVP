package ufes.br.sistemadesupermercado.mvp.model;

import java.util.Objects;

public class ProdutoModel {

    private int identificador;
    private String nome;
    private double precoCusto;
    private CategoriaModel categoria;

    public ProdutoModel(int identificador, String nome, double precoCusto, CategoriaModel categoria) {
        Objects.requireNonNull(nome, "O nome é nulo.");

        if (nome.isBlank() || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é inválido para o produto.");
        }

        Objects.requireNonNull(categoria, "A categoria é nula.");


        this.identificador = identificador;
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.categoria = categoria;
    }

    public int getIdentificador() {
        return identificador;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public CategoriaModel getCategoria() {
        return categoria;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public void setCategoria(CategoriaModel categoria) {
        this.categoria = categoria;
    }
}
