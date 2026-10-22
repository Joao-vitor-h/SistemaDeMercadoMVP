package ufes.br.sistemadesupermercado.mvp.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class CategoriaModel {

    private int id;
    private String nome;
    private BigDecimal percentualLucro;

    public CategoriaModel(int id, String nome, double percentualLucro) {
        Objects.requireNonNull(nome, "O nome é nulo.");

        if (nome.isBlank() || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome passado é inválido para a categoria.");
        }

        this.id = id;
        this.nome = nome;
        setPercentualLucro(percentualLucro);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPercentualLucro() {
        return percentualLucro;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPercentualLucro(double percentualLucro) {
        this.percentualLucro = new BigDecimal(percentualLucro).setScale(2, RoundingMode.HALF_UP);;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
               "Nome: " + nome + "\n" +
               "Percentual de Lucro: " + percentualLucro + "%\n";
    }
}
