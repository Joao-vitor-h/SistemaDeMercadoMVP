package ufes.br.sistemadesupermercado.mvp.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class HistoricoPrecoModel {

    private LocalDate dataCalculo;
    private BigDecimal percentualLucro;
    private BigDecimal precoVenda;
    private ProdutoModel produto;

    public HistoricoPrecoModel(LocalDate dataCalculo, double percentualLucro, double precoVenda, ProdutoModel produto) {
        Objects.requireNonNull(dataCalculo, "A data está nula.");
        Objects.requireNonNull(produto, "O produto está nulo.");

        this.dataCalculo = dataCalculo;
        this.percentualLucro = new BigDecimal(percentualLucro).setScale(2, RoundingMode.HALF_UP);
        this.precoVenda = new BigDecimal(precoVenda).setScale(2, RoundingMode.HALF_UP);
        this.produto = produto;
    }

    public LocalDate getDataCalculo() {
        return dataCalculo;
    }

    public BigDecimal getPercentualLucro() {
        return percentualLucro;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    @Override
    public String toString() {
        return "Data do Cálculo: " + dataCalculo.getDayOfMonth() + "/" + dataCalculo.getMonthValue() + "/" + dataCalculo.getYear() + "\n" +
               "Percentual de Lucro: R$" + percentualLucro + "\n" +
               "Preço de Venda: R$" + precoVenda + "\n" +
               "Produto: " + produto.getNome();
    }
}
