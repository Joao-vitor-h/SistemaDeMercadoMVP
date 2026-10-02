package ufes.br.sistemadesupermercado.mvp.model;

import java.util.Objects;

public class ProdutoModel {

    private int id;
    private String nome;
    private double precoCusto;
    private CategoriaModel categoria;

    public ProdutoModel(int id, String nome, double precoCusto, CategoriaModel categoria) {
        Objects.requireNonNull(nome, "O nome é nulo.");

        if (nome.isBlank() || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome é inválido para o produto.");
        }

        Objects.requireNonNull(categoria, "A categoria é nula.");


        this.id = id;
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public void setCategoria(CategoriaModel categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
               "Nome do Produto: " + nome + "\n" +
               "Preço de Custo: " + precoCusto + "\n" +
               "Categoria: " + categoria.getNome() + "\n";
    }
}
