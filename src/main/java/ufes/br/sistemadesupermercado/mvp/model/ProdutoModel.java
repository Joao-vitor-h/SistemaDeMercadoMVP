package ufes.br.sistemadesupermercado.mvp.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class ProdutoModel {

    private int id;
    private String nome;
    private BigDecimal precoCusto;
    private CategoriaModel categoria;

    public ProdutoModel(int id, String nome, double precoCusto, CategoriaModel categoria) {
        Objects.requireNonNull(nome, "O nome é nulo.");

        if (nome.isBlank() || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome é inválido para o produto.");
        }

        Objects.requireNonNull(categoria, "A categoria é nula.");

        this.id = id;
        this.nome = nome;
        setPrecoCusto(precoCusto);
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    public CategoriaModel getCategoria() {
        return categoria;
    }

    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = new BigDecimal(precoCusto).setScale(2, RoundingMode.HALF_UP);
    }

    public void setCategoria(CategoriaModel categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
               "Nome do Produto: " + nome + "\n" +
               "Preço de Custo: R$" + precoCusto + "\n" +
               "Categoria: " + categoria.getNome() + "\n";
    }
}
