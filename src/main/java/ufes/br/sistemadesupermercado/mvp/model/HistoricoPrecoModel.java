package ufes.br.sistemadesupermercado.mvp.model;

import java.time.LocalDate;
import java.util.Objects;

public class HistoricoPrecoModel {

    private LocalDate dataCalculo;
    private double percentualLucro;
    private double precoVenda;
    private ProdutoModel produto;

    public HistoricoPrecoModel(LocalDate dataCalculo, double percentualLucro, double precoVenda, ProdutoModel produto) {
        Objects.requireNonNull(dataCalculo, "A data está nula.");
        Objects.requireNonNull(produto, "O produto está nulo.");

        this.dataCalculo = dataCalculo;
        this.percentualLucro = percentualLucro;
        this.precoVenda = precoVenda;
        this.produto = produto;
    }

    public LocalDate getDataCalculo() {
        return dataCalculo;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    @Override
    public String toString() {
        return "Data do Cálculo: " + dataCalculo.getDayOfMonth() + "/" + dataCalculo.getMonthValue() + "/" + dataCalculo.getYear() + "\n" +
               "Percentual de Lucro: R$" + percentualLucro + "\n" +
               "Preço de Venda: R$" + precoVenda + "\n" +
               "Produto: " + produto.getNome();
    }
}
